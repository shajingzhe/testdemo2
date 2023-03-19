import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

import javax.servlet.http.HttpServletResponse;

/**
 * 转换html为pdf
 *
 * @author Uncle Liu
 */
public class Html2pdf {

    /**
     * 将HTML转成PD格式的文件。html文件的格式比较严格
     *
     * @param htmlFile
     * @param pdfFile
     * @throws Exception
     */
    // <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd ">
    public static void html2pdf(String htmlFile, String pdfFile) throws Exception {
        // step 1
        String url = new File(htmlFile).toURI().toURL().toString();
        System.out.println(url);
        // step 2
        OutputStream os = new FileOutputStream(pdfFile);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);

        // step 3 解决中文支持
        ITextFontResolver fontResolver = renderer.getFontResolver();
        if ("linux".equals(getCurrentOperatingSystem())) {
            //Linux下的和Windows的不一样，要自己下载，不要拷贝Windows的到Linux
            fontResolver.addFont("/usr/share/fonts/chiness/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } else {
            fontResolver.addFont("C:\\Windows\\WinSxS\\amd64_microsoft-windows-font-truetype-simsun_31bf3856ad364e35_10.0.17134.1_none_e089ab61d8d9374e\\simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        }

        renderer.layout();
        renderer.createPDF(os);
        os.close();

        System.out.println("create pdf done!!");

    }

    public static String getCurrentOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("---------当前操作系统是-----------" + os);
        return os;
    }


    public static void main(String[] args) {

        String htmlFile = "D:\\WorkSpace\\IdeaProjects\\pdf\\src\\main\\resources\\templates\\u.html";
        String pdfFile = "c:/test.pdf";
        try {
            Html2pdf.html2pdf(htmlFile, pdfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    package com.huafa.core.util;
//
//import com.huafa.core.codegenerator.utils.PathUtil;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.tool.xml.XMLWorkerFontProvider;
//import com.itextpdf.tool.xml.XMLWorkerHelper;
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//
//    /**
//     * @Auther: chenyanwu
//     * @Date: 2019/3/20 15:50
//     * @Description:
//     * @Version 1.0
//     */
//    public class PDFUtil {
//
//        private static final String FONT = "simhei.ttf";
//
//        private static Configuration freemarkerCfg = null;
//
//        static {
//            freemarkerCfg =new Configuration();
//            //freemarker的模板目录
//            try {
//                freemarkerCfg.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        /**
//         * 将freemark渲染为html，转换成pdf
//         * @param t 动态数据
//         * @param
//         * @param <T>
//         */
//        public static <T> void htmlToPdf(T t, String fileName, String template, HttpServletResponse resp) {
//            // 渲染html内容
//            String content = PDFUtil.freeMarkerRender(t, template);
//
//            Document document = new Document();
//            try {
//                resp.setCharacterEncoding("UTF-8");
//                resp.setHeader("content-Type", "application/pdf");
//                resp.setHeader("Content-Disposition",
//                        "inline;filename=" + URLEncoder.encode(fileName + ".pdf", "UTF-8"));
//                // 建立书写器
//                PdfWriter writer = PdfWriter.getInstance(document, resp.getOutputStream());
//                document.open();
//                XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
//                fontImp.register(FONT);
//                XMLWorkerHelper.getInstance().parseXHtml(writer, document,
//                        new ByteArrayInputStream(content.getBytes()), null, Charset.forName("UTF-8"), fontImp);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            } finally {
//                document.close();
//            }
//        }
//
//        /**
//         * freemarker渲染html
//         */
//        public static <T> String freeMarkerRender(T data, String htmlTmp) {
//            Writer out = new StringWriter();
//            try {
//                // 获取模板,并设置编码方式
//                Template template = freemarkerCfg.getTemplate(htmlTmp);
//                template.setEncoding("UTF-8");
//                // 合并数据模型与模板，将合并后的数据和模板写入到流中，这里使用的字符流
//                template.process(data, out);
//                out.flush();
//                return out.toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    out.close();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//    }
//
//
//    public void htmlToPdf(HttpServletResponse resp, String id) {
//        Map<String,Object> data = new HashMap();
//
//        InvoiceDetail invoiceDetail = mapper.findInvoiceDetailById(id);
//
//        String invoiceCustomId = invoiceDetail.getInvoiceCustomId();
//        InvoiceCustom invoiceCustom = invoiceCustomMapper.findInvoiceCustomById(invoiceCustomId);
//        data.put("customName", invoiceCustom.getName());
//        data.put("customTaxid", invoiceCustom.getCustomTaxid());
//        data.put("customContact", invoiceCustom.getCustomContact());
//        data.put("customBankinfo", invoiceCustom.getCustomBankinfo());
//
//        data.put("contractNumber", invoiceDetail.getContractNumber());
//        data.put("contractAmount", invoiceDetail.getContractAmount());
//        data.put("contractPeriod", invoiceDetail.getContractPeriod());
//        data.put("hasPaid", invoiceDetail.getHasPaid());
//        data.put("invoiceAmount", invoiceDetail.getInvoiceAmount());
//        data.put("bankAndDate", invoiceDetail.getBankAndDate());
//
//        Example example = new Example(InvoiceInfo.class);
//        example.createCriteria().andEqualTo("invoiceDetailId", id);
//        List<InvoiceInfo> list = infoMapper.selectByExample(example);
//        BigDecimal sum = BigDecimal.ZERO;
//        for(InvoiceInfo invoiceInfo: list) {
//            sum = sum.add(invoiceInfo.getAmount());
//        }
//        data.put("sum", sum);
//        data.put("infos", list);
//
//        String html = "template_freemarker_invoice.html";
//        PDFUtil.htmlToPdf(data, "测试", html, resp);
//    }
//


}
