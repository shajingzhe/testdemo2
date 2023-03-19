
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.style.StyleUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Excel工具类
 * </p>
 *
 * @Author REID
 * @Blog https://blog.csdn.net/qq_39035773
 * @GitHub https://github.com/BeginnerA
 * @Data 2021/12/9
 * @Version V1.0
 **/
public class ExcelTools extends ExcelUtil {

    /**
     * 获得带数据表头的{@link ExcelWriter}，默认写出到第一个sheet
     * @param treeList 表头数据
     * @param destFilePath 目标文件路径
     * @param <T> T
     * @return {@link ExcelWriter}
     */
    public static <T> ExcelWriter getIncludeTableHeadWriter(List<Tree<T>> treeList, String destFilePath) {
        ExcelWriter writer = ExcelUtil.getWriter(destFilePath);
        int i = sameLevelMaxDepth(treeList);
        createHead(writer, treeList, i, 0);
        setSizeColumn(writer);
        writer.setCurrentRow(i);
        return writer;
    }

    /**
     * 获得带数据表头并且表头有空格对角线的{@link ExcelWriter}，默认写出到第一个sheet
     * @param treeList 表头数据
     * @param destFilePath 目标文件路径
     * @param endCol – 表头空格对角线结束列（基于 0）
     * @param <T> T
     * @return {@link ExcelWriter}
     */
    public static <T> ExcelWriter getIncludeTableHeadWriter(List<Tree<T>> treeList, String destFilePath, int endCol) {
        ExcelWriter writer = ExcelUtil.getWriter(destFilePath);
        int i = sameLevelMaxDepth(treeList);
        writeSecHeadRow(writer, 0, 0, 0, 0, 0, 0, endCol, i);
        createHead(writer, treeList, i, endCol);
        setSizeColumn(writer);
        writer.setCurrentRow(i);
        return writer;
    }

    /**
     * 绘制单元格对角线
     * @param writer Excel 写入器 {@link ExcelWriter}
     * @param dx1 第一个单元格内的 x 坐标
     * @param dy1 第一个单元格内的 y 坐标
     * @param dx2 第二个单元格内的 x 坐标
     * @param dy2 第二个单元格内的 y 坐标
     * @param col1 第一个单元格的列（基于 0）
     * @param row1 第一个单元格的行（基于 0）
     * @param col2 第二个单元格的列（基于 0）
     * @param row2 第二个单元格的行（基于 0）
     */
    public static void writeSecHeadRow(ExcelWriter writer, int dx1, int dy1, int dx2, int dy2, int col1, int row1, int col2, int row2) {
        // 合并需要绘制对角线的单元格
        // firstRow – 起始行，0开始
        // lastRow – 结束行，0开始
        // firstColumn – 起始列，0开始
        // lastColumn – 结束列，0开始
        // content – 合并单元格后的内容
        // isSetHeaderStyle – 是否为合并后的单元格设置默认标题样式，只提取边框样式
        writer.merge(0, row2 - 1, 0, col2 - 1, null, false);
        Sheet sheet = writer.getSheet();
        XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = new XSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2);
        XSSFSimpleShape shape = drawing.createSimpleShape(anchor);
        // 设置图形的类型为线
        shape.setShapeType(ShapeTypes.LINE);
        // 设置填充颜色
        shape.setFillColor(0, 0, 0);
        // 设置边框线型：solid=0、dot=1、dash=2、lgDash=3、dashDot=4、lgDashDot=5、lgDashDotDot=6、sysDash=7、sysDot=8、sysDashDot=9、sysDashDotDot=10
        shape.setLineStyle(0);
        // 设置边框线颜色
        shape.setLineStyleColor(0, 0, 0);
        // 设置边框线宽,单位Point
        shape.setLineWidth(1);
        // 对角线单元格内容
        //writer.writeCellValue(0, 0, "地下      土壤");
    }

    /**
     * 创建 Excel 写入数据表头
     * 创建完成后需要使用{@link ExcelWriter} 下的 setCurrentRow() 方法设置当前所在行
     * @param writer Excel 写入器
     * @param treeList Excel 表头数据
     * @param maxDepth Excel 数据表头结束行，0开始
     * @param passCol Excel 数据表头起始列，0开始
     * @param <T> T 数据源里的对象类型
     */
    public static <T> void createHead(ExcelWriter writer, List<Tree<T>> treeList, int maxDepth, int passCol) {
        if (CollUtil.isNotEmpty(treeList)) {
            for (Tree<T> node : treeList) {
                // firstRow – 起始行，0开始
                int firstRow = writer.getCurrentRow();
                // lastRow – 结束行，0开始
                int lastRow = firstRow;
                // 当前节点深度，等于1说明没有叶子节点
                if (maxDepth(node) == 1 && firstRow < maxDepth) {
                    // 计算行合并，有可能该叶子节点不是最深节点
                    List<CharSequence> parentsName = node.getParentsName(false);
                    // 以最大深度为结束行
                    lastRow = (maxDepth - (parentsName.size() == 0 ? 1 : parentsName.size())) + firstRow;
                }

                // firstColumn – 起始列，0开始]
                int firstColumn = Math.max(writer.getColumnCount(firstRow), passCol);
                // lastColumn – 结束列，0开始
                int lastColumn = firstColumn + leafNodeSum(node.getChildren(), 0) - 1;
                if (lastRow != firstRow && lastColumn + 1 == firstColumn) {
                    lastColumn = firstColumn;
                }
                // content – 合并单元格后的内容
                // 单元格样式
                CellStyle cellStyle = (CellStyle) node.get("cellStyle");
                if (lastRow > firstRow || lastColumn > firstColumn) {
                    if (cellStyle == null) {
                        writer.merge(firstRow, lastRow, firstColumn, lastColumn, node.getName(), true);
                    }else {
                        writer.merge(firstRow, lastRow, firstColumn, lastColumn, node.getName(), cellStyle);
                    }
                }else {
                    writer.writeCellValue(firstColumn, firstRow, node.getName());
                    if (cellStyle == null) {
                        writer.setStyle(StyleUtil.createHeadCellStyle(writer.getWorkbook()), firstColumn, firstRow);
                    }else {
                        writer.setStyle(cellStyle, firstColumn, firstRow);
                    }
                }

                List<Tree<T>> children = node.getChildren();

                if(children != null && children.size() > 0) {
                    writer.setCurrentRow(firstRow + 1);
                    createHead(writer, children, maxDepth, passCol);
                }
            }

            writer.setCurrentRow(writer.getCurrentRow() - 1);

        }
    }

    /**
     * 获取当前同级节点最大叶子节点深度
     * @param treeList 同级节点列表
     * @param <T> T
     * @return 同级节点最大叶子节点深度
     */
    private static <T> int sameLevelMaxDepth(List<Tree<T>> treeList) {
        int max = 0;
        for (Tree<T> node : treeList) {
            max = Math.max(maxDepth(node), max);
        }
        return max;
    }

    /**
     * 获取当前节点树的最大深度
     * @param node 树节点
     * @param <T> T
     * @return 前节点树的最大深度
     */
    private static <T> int maxDepth(Tree<T> node) {
        if (node == null) {
            return 0;
        }
        int maxChildDepth = 0;
        List<Tree<T>> children = node.getChildren();
        if (children != null) {
            for (Tree<T> child : children) {
                int childDepth = maxDepth(child);
                maxChildDepth = Math.max(maxChildDepth, childDepth);
            }
        }
        return maxChildDepth + 1;
    }

    /**
     * 获取树的所有叶子节点数
     * @param treeList 数数据
     * @param sum 当前节点数
     * @param <T> T
     * @return 叶子节点数
     */
    private static <T> int leafNodeSum(List<Tree<T>> treeList, int sum) {
        if (CollUtil.isNotEmpty(treeList)) {
            for(Tree<T> node : treeList) {
                List<Tree<T>> children = node.getChildren();
                if(children != null && children.size() > 0) {
                    sum = leafNodeSum(children, sum);
                }else {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    /**
     * 自适应宽度
     * @param writer Excel 写入器
     */
    public static void setSizeColumn(ExcelWriter writer) {
        int size = writer.getColumnCount();
        Sheet sheet = writer.getSheet();
        for (int columnNum = 0; columnNum <= size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row currentRow;
                // 当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    Cell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }
}