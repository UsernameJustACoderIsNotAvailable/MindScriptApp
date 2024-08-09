package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.ComplexCodePart;
import compilers.codeParts.SingleLineCodePart;
import compilers.codeParts.operations.ComplexOperation;
import compilers.mathEngine.MathData;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;
import static compilers.mathEngine.MathematicalExpressionReader.readExpression;

public class Draw extends ComplexCodePart {
    enum drawType{
        clear,
        color,
        col,
        stroke,
        line,
        rect,
        lineRect,
        poly,
        linePoly,
        triangle,
        image
    }

    drawType drawType;
    ComplexOperation r, g, b, a;
    String color;
    ComplexOperation stroke; //ширина линии
    ComplexOperation x, y, x2, y2, x3, y3;
    ComplexOperation width, height;
    ComplexOperation sides;
    ComplexOperation radius, rotation;
    String image;
    ComplexOperation size;

    Draw(String arg, drawType drawType, MathData mathData){
        this.drawType = drawType;
        this.color = arg;
        switch (drawType){
            case col -> {
                this.color = arg;
                linesCount = 1;
            }
            case stroke -> {
                this.stroke = readExpression(arg, mathData);
                linesCount = 1 + stroke.linesCount;
            }
        }
    }//col, stroke

    Draw(String rExpression, String gExpression, String bExpression, drawType drawType, MathData mathData) {
        this.drawType = drawType;
        r = readExpression(rExpression, mathData);
        g = readExpression(gExpression, mathData);
        b = readExpression(bExpression, mathData);
        linesCount = 1 + r.linesCount + g.linesCount + b.linesCount;
    }//clear
    Draw(String arg1, String arg2, String arg3, String arg4, drawType drawType, MathData mathData){
        this.drawType = drawType;
        switch (drawType){
            case color -> {
                r = readExpression(arg1, mathData);
                g = readExpression(arg2, mathData);
                b = readExpression(arg3, mathData);
                a = readExpression(arg4, mathData);
                linesCount = 1 + r.linesCount + g.linesCount + b.linesCount + a.linesCount;
            }
            case line -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                x2 = readExpression(arg3, mathData);
                y2 = readExpression(arg4, mathData);
                linesCount = 1 + x.linesCount + y.linesCount + x2.linesCount + y2.linesCount;
            }
            case rect, lineRect -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                width = readExpression(arg3, mathData);
                height = readExpression(arg4, mathData);
                linesCount = 1 + x.linesCount + y.linesCount + width.linesCount + height.linesCount;
            }
        }
    }//color, line, rect, lineRect
    Draw(String arg1, String arg2, String arg3, String arg4, String arg5, drawType drawType, MathData mathData){
        this.drawType = drawType;
        switch (drawType){
            case poly, linePoly -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                sides = readExpression(arg3, mathData);
                radius = readExpression(arg4, mathData);
                rotation = readExpression(arg5, mathData);
                linesCount = 1 + x.linesCount + y.linesCount + sides.linesCount + radius.linesCount + rotation.linesCount;
            }
            case image -> {
                x = readExpression(arg1, mathData);
                y = readExpression(arg2, mathData);
                this.image = arg3;
                sides = readExpression(arg3, mathData);
                rotation = readExpression(arg5, mathData);
                linesCount = 1 + x.linesCount + y.linesCount + sides.linesCount + rotation.linesCount;
            }
        }
    }//poly, linePoly, image
    Draw(String xExpression, String yExpression, String x2Expression, String y2Expression, String x3Expression, String y3Expression, drawType drawType, MathData mathData){
        this.drawType = drawType;
        x = readExpression(xExpression, mathData);
        y = readExpression(yExpression, mathData);
        x2 = readExpression(x2Expression, mathData);
        y2 = readExpression(y2Expression, mathData);
        x3 = readExpression(x3Expression, mathData);
        y3 = readExpression(y3Expression, mathData);
    }//triangle

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        switch (drawType){
            case clear -> {
                allCycleCodeParts.add(r);
                allCycleCodeParts.add(g);
                allCycleCodeParts.add(b);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw clear %s %s %s",
                                getVarNameWithPrefix(r.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(g.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(b.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case color -> {
                allCycleCodeParts.add(r);
                allCycleCodeParts.add(g);
                allCycleCodeParts.add(b);
                allCycleCodeParts.add(a);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw color %s %s %s %s",
                                getVarNameWithPrefix(r.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(g.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(b.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(a.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case col -> {return String.format("draw col %s",
                    getVarNameWithPrefix(color, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case stroke -> {
                allCycleCodeParts.add(stroke);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw stroke %s",
                                getVarNameWithPrefix(stroke.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case line -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(x2);
                allCycleCodeParts.add(y2);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw line %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(x2.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y2.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case rect, lineRect -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(width);
                allCycleCodeParts.add(height);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw %s %s %s %s %s",
                                drawType,
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(width.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(height.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case poly, linePoly -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(sides);
                allCycleCodeParts.add(radius);
                allCycleCodeParts.add(rotation);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw %s %s %s %s %s",
                                drawType,
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(sides.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(radius.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(rotation.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case triangle -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(x2);
                allCycleCodeParts.add(y2);
                allCycleCodeParts.add(x3);
                allCycleCodeParts.add(y3);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("draw triangle %s %s %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(x2.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y2.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(x3.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y3.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
            case image -> {
                allCycleCodeParts.add(x);
                allCycleCodeParts.add(y);
                allCycleCodeParts.add(sides);
                allCycleCodeParts.add(rotation);
                return getAllCycleCodePartsAsCompiledCode(previousCPLastLineIndex, nameSpaceIndex, uncompiledCode) +
                        String.format("image %s %s %s %s %s",
                                getVarNameWithPrefix(x.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(y.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(image, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(sides.finalVarName, nameSpaceIndex, uncompiledCode),
                                getVarNameWithPrefix(rotation.finalVarName, nameSpaceIndex, uncompiledCode)
                        ) + "\n";}
        }
        return null;
    }

}



