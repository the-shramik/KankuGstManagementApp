package com.kanku.service.impl;

import com.kanku.dao.IProductRepository;
import com.kanku.dao.ISaleRepository;
import com.kanku.dao.IUserRepo;
import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.MyUser;
import com.kanku.model.Sale;
import com.kanku.model.SaleMonthlySummary;
import com.kanku.model.batchdto.BatchSaleProduct;
import com.kanku.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleServiceImpl implements ISaleService {

    @Autowired
    private ISaleRepository saleRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public BatchSaleProduct saleProduct(BatchSaleProduct batchSaleProduct) throws BillNumberAlreadyExistsException {

        if(saleRepository.getSaleBySaleBillNo(batchSaleProduct.getBillNumber()).isEmpty()){

            batchSaleProduct.getSaleProducts().forEach(saleProducts -> {
                Sale sale=new Sale();

                sale.setProduct(saleProducts.getProduct());
                sale.setSaleBillNo(batchSaleProduct.getBillNumber());
                sale.setSaleQuantity(saleProducts.getProductQuantity());
                sale.setTotalProductSaleAmount(productRepository.findById(saleProducts.getProduct().getProductId()).get().getProductPrice()*saleProducts.getProductQuantity());
                sale.setSaleDate(batchSaleProduct.getSaleDate());
                sale.setCGST(saleProducts.getCGst());
                sale.setSGST(saleProducts.getSGst());
                sale.setIGST(saleProducts.getIGst());
                sale.setCustomerName(batchSaleProduct.getCustomerName());
                sale.setCustomerAddress(batchSaleProduct.getCustomerAddress());

                MyUser myUser =userRepo.findById(1).get();
                sale.setSellerGstNumber(myUser.getUserGstNumber());

                saleRepository.save(sale);
            });

            batchSaleProduct.setMyUser(userRepo.findById(1).get());
            return batchSaleProduct;
        }
        else{
            throw new BillNumberAlreadyExistsException("The bill number already exists!");
        }

    }
    @Override
    public List<SaleMonthlySummary> getMonthlySummary(LocalDate startDate, LocalDate endDate) {

        List<SaleMonthlySummary> saleMonthlySummaries=new ArrayList<>();


        saleRepository.getSalesByMonthSummary(startDate,endDate).forEach(sale -> {
            SaleMonthlySummary saleMonthlySummary=new SaleMonthlySummary();

            saleMonthlySummary.setSaleDate(sale.getSaleDate());
            saleMonthlySummary.setSaleBillNumber(sale.getSaleBillNo());
            saleMonthlySummary.setGoodsAmount(sale.getProduct().getProductPrice());
            saleMonthlySummary.setPaymentType("DUMMY");
            saleMonthlySummary.setSallerGstNumber(sale.getSellerGstNumber());

            BigDecimal bdd = new BigDecimal(sale.getSaleQuantity()*sale.getProduct().getProductPrice()).setScale(2, RoundingMode.DOWN);
            double formattedGoodsAmount = bdd.doubleValue();
            saleMonthlySummary.setGoodsAmount(formattedGoodsAmount);

            if(sale.getCGST()!=null && sale.getSGST()!=null) {
                double cGSTAmount = sale.getProduct().getProductPrice()*sale.getCGST()/100;
                double sGSTAmount = sale.getProduct().getProductPrice()*sale.getSGST()/100;

                double totalCGST=0;
                double totalSGST=0;

                double finalAmount=sale.getSaleQuantity()*sale.getProduct().getProductPrice();

                for (int i=1;i<=sale.getSaleQuantity();i++){
                    finalAmount+=cGSTAmount+sGSTAmount;
                    totalCGST+=sale.getProduct().getProductPrice()*sale.getCGST()/100;
                    totalSGST+=sale.getProduct().getProductPrice()*sale.getSGST()/100;
                }
                BigDecimal bd = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd.doubleValue();
                saleMonthlySummary.setFinalAmount(formattedFinalAmount);

                BigDecimal bd2 = new BigDecimal(totalCGST).setScale(2, RoundingMode.DOWN);
                double formattedCGST = bd2.doubleValue();
                saleMonthlySummary.setCGstAmount(formattedCGST);

                BigDecimal bd3 = new BigDecimal(totalSGST).setScale(2, RoundingMode.DOWN);
                double formattedSGST = bd3.doubleValue();
                saleMonthlySummary.setSGstAmount(formattedSGST);

                BigDecimal bd4 = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd4.doubleValue();
                saleMonthlySummary.setRoundUpAmount(roundUpAmount);

                saleMonthlySummary.setIGstAmount(0.0);

            } else if (sale.getIGST()!=null) {

                double finalAmount=sale.getSaleQuantity()*sale.getProduct().getProductPrice();

                double totalIGST=0;

                for (int i=1;i<=sale.getSaleQuantity();i++){
                    finalAmount+=sale.getProduct().getProductPrice()*sale.getIGST()/100;
                    totalIGST+=sale.getProduct().getProductPrice()*sale.getIGST()/100;
                }


                BigDecimal bd = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd.doubleValue();
                saleMonthlySummary.setFinalAmount(formattedFinalAmount);


                BigDecimal bd2 = new BigDecimal(totalIGST).setScale(2, RoundingMode.DOWN);
                double formattedIGST = bd2.doubleValue();
                saleMonthlySummary.setIGstAmount(formattedIGST);

                saleMonthlySummary.setCGstAmount(0.0);
                saleMonthlySummary.setSGstAmount(0.0);

                BigDecimal bd3 = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd3.doubleValue();
                saleMonthlySummary.setRoundUpAmount(roundUpAmount);
            }else if(sale.getCGST()!=null) {


                double finalAmount=sale.getSaleQuantity()*sale.getProduct().getProductPrice();
                double totalCGST=0;

                for (int i=1;i<=sale.getSaleQuantity();i++){
                    finalAmount+=sale.getProduct().getProductPrice()*sale.getCGST()/100;
                    totalCGST+=sale.getProduct().getProductPrice()*sale.getCGST()/100;
                }
                BigDecimal bd2 = new BigDecimal(totalCGST).setScale(2, RoundingMode.DOWN);
                double formattedCGST = bd2.doubleValue();
                saleMonthlySummary.setCGstAmount(formattedCGST);

                BigDecimal bd3 = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd3.doubleValue();
                saleMonthlySummary.setFinalAmount(formattedFinalAmount);

                BigDecimal bd = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd.doubleValue();
                saleMonthlySummary.setRoundUpAmount(roundUpAmount);
            }else if (sale.getSGST()!=null) {
                double finalAmount=sale.getSaleQuantity()*sale.getProduct().getProductPrice();
                double totalSGST=0;

                for (int i=1;i<=sale.getSaleQuantity();i++){
                    finalAmount+=sale.getProduct().getProductPrice()*sale.getSGST()/100;
                    totalSGST+=sale.getProduct().getProductPrice()*sale.getSGST()/100;
                }

                BigDecimal bd2 = new BigDecimal(totalSGST).setScale(2, RoundingMode.DOWN);
                double formattedSGST = bd2.doubleValue();
                saleMonthlySummary.setSGstAmount(formattedSGST);

                BigDecimal bd3 = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd3.doubleValue();
                saleMonthlySummary.setFinalAmount(formattedFinalAmount);

                BigDecimal bd = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd.doubleValue();
                saleMonthlySummary.setRoundUpAmount(roundUpAmount);
            }else {
                saleMonthlySummary.setFinalAmount(sale.getProduct().getProductPrice());
            }

            saleMonthlySummaries.add(saleMonthlySummary);

        });


        return saleMonthlySummaries;
    }
}
