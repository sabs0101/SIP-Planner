package com.sipplanner.controller;

import com.sipplanner.model.Bank;
import com.sipplanner.model.InvestmentResult;
import org.apache.hc.client5.http.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import yahoofinance.YahooFinance;
import yahoofinance.Stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InvestmentController {

    private final List<Bank> fdBanks = List.of(
        new Bank("HDFC Bank",6.5),
        new Bank("ICICI Bank",6.0),
        new Bank("SBI",5.75),
        new Bank("Axis Bank",6.25),
        new Bank("Kotak Mahindra",6.0),
        new Bank("PNB",5.5),
        new Bank("Bank of Baroda",5.6),
        new Bank("IDFC First",6.3),
        new Bank("IndusInd",6.1),
        new Bank("Yes Bank",6.2)
    );

    private final List<Bank> ppfBanks = fdBanks;

    @GetMapping("/")
    public String index(Model model){
        return "index";
    }

    @PostMapping("/api/fdppf")
    @ResponseBody
    public List<InvestmentResult> fdppf(@RequestBody java.util.Map<String,String> data){
        double monthly = Double.parseDouble(data.get("amount"));
        int years = Integer.parseInt(data.get("duration"));
        String plan = data.get("plan");
        int months = years*12;

        List<Bank> banks = plan.equals("FD")?fdBanks:ppfBanks;
        List<InvestmentResult> results = new ArrayList<>();
        double maxMaturity = 0;
        Bank bestBank = null;

        for(Bank bank:banks){
            double r = bank.getRate()/12/100;
            double maturity = monthly*((Math.pow(1+r,months)-1)/r)*(1+r);

            InvestmentResult res = new InvestmentResult();
            res.setName(bank.getName());
            res.setMaturity(Math.round(maturity));
            results.add(res);

            if(maturity>maxMaturity){
                maxMaturity=maturity;
                bestBank=bank;
            }
        }

        for(InvestmentResult res:results){
            if(res.getName().equals(bestBank.getName())) res.setRecommended(true);
            else res.setRecommended(false);
        }

        return results;
    }

    @PostMapping("/api/mf")
    @ResponseBody
    public List<InvestmentResult> mf(@RequestBody java.util.Map<String,String> data){
        double monthly = Double.parseDouble(data.get("amount"));
        int years = Integer.parseInt(data.get("duration"));
        int months = years*12;

        List<InvestmentResult> results = new ArrayList<>();
        double maxMaturity = 0;
        String bestMF = null;

        try {
            String response = Request.get("https://api.mfapi.in/mf").execute().returnContent().asString();
            JSONArray mfList = new JSONArray(response);
            int count=0;
            for(int i=0;i<mfList.length();i++){
                if(count>=10) break;
                JSONObject mf = mfList.getJSONObject(i);
                String code = mf.getString("schemeCode");
                try {
                    String mfDataRaw = Request.get("https://api.mfapi.in/mf/"+code).execute().returnContent().asString();
                    JSONObject mfData = new JSONObject(mfDataRaw);
                    JSONArray navHistory = mfData.getJSONArray("data");
                    if(navHistory.length()==0) continue;

                    double totalUnits = 0;
                    for(int j=0;j<Math.min(months, navHistory.length());j++){
                        double nav = navHistory.getJSONObject(j).getDouble("nav");
                        totalUnits += monthly/nav;
                    }

                    double latestNav = navHistory.getJSONObject(0).getDouble("nav");
                    double maturity = totalUnits*latestNav;

                    InvestmentResult res = new InvestmentResult();
                    res.setName(mfData.getJSONObject("meta").getString("scheme_name"));
                    res.setCode(code);
                    res.setMaturity(Math.round(maturity));
                    results.add(res);

                    if(maturity>maxMaturity){
                        maxMaturity=maturity;
                        bestMF=res.getName();
                    }

                    count++;
                }catch(Exception e){ continue; }
            }

            for(InvestmentResult res:results){
                if(res.getName().equals(bestMF)) res.setRecommended(true);
                else res.setRecommended(false);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return results;
    }

    @PostMapping("/api/stocks")
    @ResponseBody
    public List<InvestmentResult> stocks(@RequestBody java.util.Map<String,String> data){
        double monthly = Double.parseDouble(data.get("amount"));
        int years = Integer.parseInt(data.get("duration"));
        int months = years*12;

        String[] symbols = {"INFY","TCS","HDFCBANK.NS","RELIANCE.NS","ICICIBANK.NS","KOTAKBANK.NS","LT.NS","HINDUNILVR.NS","SBIN.NS","AXISBANK.NS"};

        List<InvestmentResult> results = new ArrayList<>();
        double maxMaturity = 0;
        String bestStock=null;

        try {
            for(String symbol:symbols){
                Stock stock = YahooFinance.get(symbol);
                BigDecimal price = stock.getQuote().getPrice();
                if(price==null) continue;

                double totalUnits = monthly*months/price.doubleValue();
                double maturity = totalUnits*price.doubleValue();

                InvestmentResult res = new InvestmentResult();
                res.setName(symbol);
                res.setPrice(price.doubleValue());
                res.setMaturity(Math.round(maturity));
                results.add(res);

                if(maturity>maxMaturity){
                    maxMaturity=maturity;
