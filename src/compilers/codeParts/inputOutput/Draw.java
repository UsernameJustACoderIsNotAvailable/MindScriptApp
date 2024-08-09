package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

import static compilers.codeParts.NameSpacesMethods.getVarNameWithPrefix;

public class Draw extends SingleLineCodePart {
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
    String r, g, b, a;
    String color;
    String stroke; //ширина линии
    String x, y, x2, y2, x3, y3;
    String width, height;
    String sides;
    String radius, rotation;
    String image;
    String size;

    Draw(String arg, drawType drawType){
        this.drawType = drawType;
        switch (drawType){
            case col -> {
                this.color = arg;
            }
            case stroke -> {
                this.stroke = arg;
            }
        }
    }//col, stroke
    Draw(String r, String g, String b, drawType drawType) {
        this.drawType = drawType;
        this.r = r;
        this.g = g;
        this.b = b;
    }//clear
    Draw(String arg1, String arg2, String arg3, String arg4, drawType drawType){
        this.drawType = drawType;
        switch (drawType){
            case color -> {
                this.r = arg1;
                this.g = arg2;
                this.b = arg3;
                this.a = arg4;
            }
            case line -> {
                this.x = arg1;
                this.y = arg2;
                this.x2 = arg3;
                this.y2 = arg4;
            }
            case rect, lineRect -> {
                this.x = arg1;
                this.y = arg2;
                this.width = arg3;
                this.height = arg4;
            }
        }
    }//color, line, rect, lineRect
    Draw(String arg1, String arg2, String arg3, String arg4, String arg5, drawType drawType){
        this.drawType = drawType;
        switch (drawType){
            case poly, linePoly -> {
                this.x = arg1;
                this.y = arg2;
                this.sides = arg3;
                this.radius = arg4;
                this.rotation = arg5;
            }
            case image -> {
                this.x = arg1;
                this.y = arg2;
                this.image = arg3;
                this.sides = arg4;
                this.rotation = arg5;
            }
        }
    }//poly, linePoly, image
    Draw(String x, String y, String x2, String y2, String x3, String y3, drawType drawType){
        this.drawType = drawType;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }//triangle

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        switch (drawType){
            case clear -> {return String.format("draw clear %s %s %s",
                    getVarNameWithPrefix(r, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(g, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(b, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case color -> {return String.format("draw color %s %s %s %s",
                    getVarNameWithPrefix(r, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(g, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(b, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(a, nameSpaceIndex, uncompiledCode)
                    ) + "\n";}
            case col -> {return String.format("draw col %s",
                    getVarNameWithPrefix(color, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case stroke -> {return String.format("draw stroke %s",
                    getVarNameWithPrefix(stroke, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case line -> {return String.format("draw line %s %s %s %s",
                    getVarNameWithPrefix(x, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(x2, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y2, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case rect, lineRect -> {return String.format("draw rect %s %s %s %s",
                    getVarNameWithPrefix(x, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(width, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(height, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case poly, linePoly -> {return String.format("draw linePoly %s %s %s %s, %s",
                    getVarNameWithPrefix(x, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(sides, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(radius, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(rotation, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case triangle -> {return String.format("draw triangle %s %s %s %s, %s, %s",
                    getVarNameWithPrefix(x, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(x2, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y2, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(x3, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y3, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
            case image -> {return String.format("draw poly %s %s %s %s, %s",
                    getVarNameWithPrefix(x, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(y, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(image, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(sides, nameSpaceIndex, uncompiledCode),
                    getVarNameWithPrefix(rotation, nameSpaceIndex, uncompiledCode)
            ) + "\n";}
        }
        return null;
    }

}



