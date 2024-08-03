package compilers.codeParts.inputOutput;

import compilers.UncompiledCode;
import compilers.codeParts.SingleLineCodePart;

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
    int r, g, b, a;
    String color;
    float stroke; //ширина линии
    int x, y, x2, y2, x3, y3;
    int width, height;
    int sides;
    Float radius, rotation;
    String image;
    int size;

    Draw(int r, int g, int b, drawType drawType) {
        this.drawType = drawType;
        this.r = r;
        this.g = g;
        this.b = b;
    }//clear
    Draw(int arg1, int arg2, int arg3, int arg4, drawType drawType){
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
    Draw(String color, drawType drawType){
        this.drawType = drawType;
        this.color = color;
    }//col
    Draw(float stroke, drawType drawType){
        this.drawType = drawType;
        this.stroke = stroke;
    }//stroke
    Draw(int x, int y, int sides, float radius, float rotation, drawType drawType){
        this.drawType = drawType;
        this.x = x;
        this.y = y;
        this.sides = sides;
        this.radius = radius;
        this.rotation = rotation;
    }//poly, linePoly
    Draw(int x, int y, int x2, int y2, int x3, int y3, drawType drawType){
        this.drawType = drawType;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }//triangle
    Draw(int x, int y, String image, int size, float rotation, drawType drawType){
        this.drawType = drawType;
        this.x = x;
        this.y = y;
        this.image = image;
        this.size = size;
        this.rotation = rotation;
    }//image

    @Override
    public String getAsCompiledCode(int previousCPLastLineIndex, int nameSpaceIndex, UncompiledCode uncompiledCode) {
        switch (drawType){
            case clear -> {return String.format("draw clear %s %s %s", r, g, b) + "\n";}
            case color -> {return String.format("draw color %s %s %s %s", r, g, b, a) + "\n";}
            case col -> {return String.format("draw col %s", color) + "\n";}
            case stroke -> {return String.format("draw stroke %s", stroke) + "\n";}
            case line -> {return String.format("draw line %s %s %s %s", x, y, x2, y2) + "\n";}
            case rect -> {return String.format("draw rect %s %s %s %s", x, y, width, height) + "\n";}
            case lineRect -> {return String.format("draw lineRect %s %s %s %s", x, y, width, height) + "\n";}
            case poly -> {return String.format("draw poly %s %s %s %s, %s", x, y, sides, radius, rotation) + "\n";}
            case linePoly -> {return String.format("draw linePoly %s %s %s %s, %s", x, y, sides, radius, rotation) + "\n";}
            case triangle -> {return String.format("draw triangle %s %s %s %s, %s, %s", x, y, x2, y2, x3, y3) + "\n";}
            case image -> {return String.format("draw poly %s %s %s %s, %s", x, y, image, sides, rotation) + "\n";}
        }
        return null;
    }
    // TODO: 03.08.2024 пропустить тут все аргументы через функцию для префиксов
}



