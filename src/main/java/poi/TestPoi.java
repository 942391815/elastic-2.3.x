package poi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiaogu on 2017/1/10.
 */
public class TestPoi {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(new File("D:\\20160807172927448.xls"));
        List<Map<String, String>> result = analysisExcel(fis);

        for (Map<String,String> each:result){
            System.out.println(each);
        }
    }

    public static List<Map<String, String>> analysisExcel(FileInputStream fis) throws IOException {
        POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fis);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        List<String> titleList = titleList();

        List<Map<String,String>> result = new ArrayList<Map<String,String>>();

        for (int i = 2; i < physicalNumberOfRows; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row==null||row.getCell(0) == null) {
                continue;
            }
            Map<String,String > each = new HashMap<String,String>();
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            for (int j = 0; j < physicalNumberOfCells; j++) {
                HSSFCell cell = row.getCell(j);
                String cellValue = null;
                if(titleList.get(j).equals("code")||titleList.get(j).equals("rank")||titleList.get(j).equals("job_code")){
                    String cellValue1 = getCellValue(cell);
                    if(cellValue1!=null){
                        BigDecimal bigDecimal =new BigDecimal(cellValue1);
                        cellValue = bigDecimal.intValue()+"";
                    }
                }else {
                    cellValue = getCellValue(cell);
                }
                if(cellValue!=null){
                    each.put(titleList.get(j),cellValue);
                }
            }
            if(each.size()>0){
                result.add(each);
            }
        }
        return result;
    }

    public static String getCellValue(HSSFCell cell){
        DecimalFormat df = new DecimalFormat("0.00");
        String result=null ;
        switch (cell.getCellType()){
            case HSSFCell.CELL_TYPE_STRING:
                if(NumberUtils.isNumber(cell.getStringCellValue())){
                    result = df.format(cell.getNumericCellValue());
                }else {
                    result= cell.getStringCellValue();
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                result = cell.getBooleanCellValue()+"";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                result = df.format(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                result = df.format(cell.getNumericCellValue());
                break;
        }
        return result;
    }
    public static List<String> titleList(){
        List list = new ArrayList();
        list.add("code");
        list.add("name");
        list.add("job_name");
        list.add("job_code");
        list.add("written_score");
        list.add("half_written_score");
        list.add("Interview_score");
        list.add("half_Interview_score");
        list.add("all_score");
        list.add("rank");
        list.add("remark");
        return list;
    }
}
