package io.github.josephmtinangi.javadynamicreports.controllers;

import io.github.josephmtinangi.javadynamicreports.models.Product;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Controller
@RequestMapping(path = "/")
public class ReportController {

    @RequestMapping(path = "", method = RequestMethod.GET)
    public void generate(HttpServletResponse response) {


        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 2, 2500000.00));
        products.add(new Product("Keyboard", 3, 15000.00));
        products.add(new Product("Mouse", 5, 10000.00));

        try {

            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle
                    .setBackgroundColor(Color.LIGHT_GRAY));


            report()
                    .setColumnTitleStyle(columnTitleStyle)
                    .setDataSource(new JRBeanCollectionDataSource(products))
                    .highlightDetailEvenRows()
                    .title(cmp.text("Getting started").setStyle(boldCenteredStyle))
                    .columns(
                            col.column("Item", "item", type.stringType()),
                            col.column("Quantity", "quantity", type.integerType()),
                            col.column("Unit Price", "unitPrice", type.doubleType())
                    )
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
                    .toPdf(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
