package com.kanku.service.impl;

import com.kanku.dao.IProductRepository;
import com.kanku.dao.IPurchaseRepository;
import com.kanku.dao.ISupplierRepository;
import com.kanku.dao.IUserRepo;
import com.kanku.excption.BillNumberAlreadyExistsException;
import com.kanku.model.Purchase;
import com.kanku.model.PurchaseMonthlySummary;
import com.kanku.model.Supplier;
import com.kanku.model.batchdto.BatchPurchaseProduct;
import com.kanku.service.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseProductImpl implements IPurchaseService {

    @Autowired
    private IPurchaseRepository purchaseRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISupplierRepository supplierRepository;

    @Override
    public BatchPurchaseProduct purchaseProduct(BatchPurchaseProduct batchPurchaseProduct) throws BillNumberAlreadyExistsException {

        System.out.println("##########"+batchPurchaseProduct);

      if(purchaseRepository.getPurchaseByPurchaseBillNo(batchPurchaseProduct.getBillNumber()).isEmpty()) {
          batchPurchaseProduct.getPurchaseProducts().forEach(purchaseProducts -> {
              Purchase purchase = new Purchase();
              purchase.setProduct(purchaseProducts.getProduct());
              purchase.setPurchaseBillNo(batchPurchaseProduct.getBillNumber());
              purchase.setProductQuantity(purchaseProducts.getProductQuantity());
              purchase.setCGST(purchaseProducts.getCGst());
              purchase.setSGST(purchaseProducts.getSGst());
              purchase.setIGST(purchaseProducts.getIGst());
              purchase.setSupplier(batchPurchaseProduct.getSupplier());
              purchase.setPurchaseDate(batchPurchaseProduct.getPurchaseDate());
              purchase.setTotalPurchaseAmount(productRepository.findById(purchaseProducts.getProduct().getProductId()).get().getProductPrice() * purchaseProducts.getProductQuantity());

              Supplier supplier =supplierRepository.findById(batchPurchaseProduct.getSupplier().getSupplierId()).get();
              purchase.setSupplierGstNumber(supplier.getSupplierGSTNo());

              purchaseRepository.save(purchase);

          });
          return batchPurchaseProduct;
      }
      else{
          System.out.println(purchaseRepository.getPurchaseByPurchaseBillNo(batchPurchaseProduct.getBillNumber()));
          throw new BillNumberAlreadyExistsException("The bill number already exists!");
      }
    }

    @Override
    public List<PurchaseMonthlySummary> getMonthlySummary(LocalDate startDate, LocalDate endDate) {

        List<PurchaseMonthlySummary> purchaseMonthlySummaries=new ArrayList<>();

        purchaseRepository.getPurchasesByMonthSummary(startDate,endDate).forEach(purchase -> {

            PurchaseMonthlySummary purchaseMonthlySummary=new PurchaseMonthlySummary();

            purchaseMonthlySummary.setPurchaseDate(purchase.getPurchaseDate());
            purchaseMonthlySummary.setSupplierName(purchase.getSupplier().getSupplierName());
            purchaseMonthlySummary.setSupplierGstNumber(purchase.getSupplier().getSupplierGSTNo());
            purchaseMonthlySummary.setBillNumber(purchase.getPurchaseBillNo());

            BigDecimal bdd = new BigDecimal(purchase.getProductQuantity()*purchase.getProduct().getProductPrice()).setScale(2, RoundingMode.DOWN);
            double formattedGoodsAmount = bdd.doubleValue();
            purchaseMonthlySummary.setGoodsAmount(formattedGoodsAmount);


            if(purchase.getCGST()!=null && purchase.getSGST()!=null) {
                double cGSTAmount = purchase.getProduct().getProductPrice()*purchase.getCGST()/100;
                double sGSTAmount = purchase.getProduct().getProductPrice()*purchase.getSGST()/100;

                double totalCGST=0;
                double totalSGST=0;

               double finalAmount=purchase.getProductQuantity()*purchase.getProduct().getProductPrice();

                for (int i=1;i<=purchase.getProductQuantity();i++){
                     finalAmount+=cGSTAmount+sGSTAmount;
                     totalCGST+=purchase.getProduct().getProductPrice()*purchase.getCGST()/100;
                     totalSGST+=purchase.getProduct().getProductPrice()*purchase.getSGST()/100;
                }

                BigDecimal bd = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd.doubleValue();
                purchaseMonthlySummary.setFinalAmount(formattedFinalAmount);

                BigDecimal bd2 = new BigDecimal(totalCGST).setScale(2, RoundingMode.DOWN);
                double formattedCGST = bd2.doubleValue();
                purchaseMonthlySummary.setCGst(formattedCGST);


                BigDecimal bd3 = new BigDecimal(totalSGST).setScale(2, RoundingMode.DOWN);
                double formattedSGST = bd3.doubleValue();
                purchaseMonthlySummary.setSGst(formattedSGST);

                purchaseMonthlySummary.setIGst(0.0);

                BigDecimal bd4 = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd4.doubleValue();
                purchaseMonthlySummary.setRoundUpAmount(roundUpAmount);

            } else if (purchase.getIGST()!=null) {

                double finalAmount=purchase.getProductQuantity()*purchase.getProduct().getProductPrice();

                double totalIGST=0;

                for (int i=1;i<=purchase.getProductQuantity();i++){
                    finalAmount+=purchase.getProduct().getProductPrice()*purchase.getIGST()/100;
                    totalIGST+=purchase.getProduct().getProductPrice()*purchase.getIGST()/100;
                }


                BigDecimal bd = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd.doubleValue();
                purchaseMonthlySummary.setFinalAmount(formattedFinalAmount);


                BigDecimal bd2 = new BigDecimal(totalIGST).setScale(2, RoundingMode.DOWN);
                double formattedIGST = bd2.doubleValue();
                purchaseMonthlySummary.setIGst(formattedIGST);

                purchaseMonthlySummary.setSGst(0.0);
                purchaseMonthlySummary.setCGst(0.0);

                BigDecimal bd3 = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd3.doubleValue();
                purchaseMonthlySummary.setRoundUpAmount(roundUpAmount);

            }else if(purchase.getCGST()!=null) {


                double finalAmount=purchase.getProductQuantity()*purchase.getProduct().getProductPrice();
                double totalCGST=0;

                for (int i=1;i<=purchase.getProductQuantity();i++){
                    finalAmount+=purchase.getProduct().getProductPrice()*purchase.getCGST()/100;
                    totalCGST+=purchase.getProduct().getProductPrice()*purchase.getCGST()/100;
                }

                BigDecimal bd2 = new BigDecimal(totalCGST).setScale(2, RoundingMode.DOWN);
                double formattedCGST = bd2.doubleValue();
                purchaseMonthlySummary.setCGst(formattedCGST);


                BigDecimal bd3 = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd3.doubleValue();
                purchaseMonthlySummary.setFinalAmount(formattedFinalAmount);

                purchaseMonthlySummary.setSGst(0.0);
                purchaseMonthlySummary.setIGst(0.0);

                BigDecimal bd = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd.doubleValue();
                purchaseMonthlySummary.setRoundUpAmount(roundUpAmount);

            }else if (purchase.getSGST()!=null) {
                double finalAmount=purchase.getProductQuantity()*purchase.getProduct().getProductPrice();
                double totalSGST=0;

                for (int i=1;i<=purchase.getProductQuantity();i++){
                    finalAmount+=purchase.getProduct().getProductPrice()*purchase.getSGST()/100;
                    totalSGST+=purchase.getProduct().getProductPrice()*purchase.getSGST()/100;
                }

                BigDecimal bd2 = new BigDecimal(totalSGST).setScale(2, RoundingMode.DOWN);
                double formattedSGST = bd2.doubleValue();
                purchaseMonthlySummary.setSGst(formattedSGST);

                BigDecimal bd3 = new BigDecimal(finalAmount).setScale(2, RoundingMode.DOWN);
                double formattedFinalAmount = bd3.doubleValue();
                purchaseMonthlySummary.setFinalAmount(formattedFinalAmount);

                purchaseMonthlySummary.setIGst(0.0);
                purchaseMonthlySummary.setCGst(0.0);

                BigDecimal bd = new BigDecimal(Math.round(finalAmount)).setScale(2, RoundingMode.DOWN);
                double roundUpAmount = bd.doubleValue();
                purchaseMonthlySummary.setRoundUpAmount(roundUpAmount);
            }else {
                purchaseMonthlySummary.setFinalAmount(purchase.getProduct().getProductPrice());
            }


            purchaseMonthlySummaries.add(purchaseMonthlySummary);
        });
        return purchaseMonthlySummaries;
    }
}
