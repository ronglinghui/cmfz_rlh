package ExcleText;

import com.baizhi.App;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class OutExcleText {
    /*private String id;//主键
    private String username;//登录名
    private String password;//密码
    private String other;//预留字段*/
    @Test
    public void text1() {
        //1、创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2、创建工作部
        HSSFSheet sheet = workbook.createSheet();
        //3、创建行0：代表的是第一行
        HSSFRow row = sheet.createRow(0);
        //4、创建单元格
        HSSFCell cell = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        //5、单元格设值
        cell.setCellValue("hello");
        cell1.setCellValue(true);
        cell2.setCellValue(new Date());
        //6、指定输出位置及文件名
        try {
            workbook.write(new File("D:/test.xls"));
            //关流
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("完成");
    }

    //添加样式=====================================
    public void test2() {
        //创建excle文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建字体样式
        HSSFFont font = workbook.createFont();
        //  加粗
        // font.se

        //创建工作部
        HSSFSheet sheet = workbook.createSheet();
    }
}
