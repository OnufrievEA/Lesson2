package com.example.lesson2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ThermometerView extends View {

    private int thermometerColor = Color.DKGRAY;
    private int levelColor = Color.RED;
    private int scaleColor = Color.WHITE;
    private String text;

    //Изображение термометра
    private RectF thermometertRectangle = new RectF();
    private RectF headRectangle = new RectF();

    // Изображение уровня температуры
    private Rect scaleRectangle = new Rect();
    private Rect zeroLevelRectangle = new Rect();
    private Rect temperatureRectangle = new Rect();

    // "Краска" термометра
    private Paint thermometerPaint;
    // "Краска" температуры
    private Paint temperaturePaint;
    // "Краска" шкалы
    private Paint scalePaint;
    // "Краска" нулевой отметки
    private Paint zeroLevelPaint;
    // Ширина элемента
    private int width = 0;
    // Высота элемента
    private int height = 0;
    // Радиус закругления термометра
    private int round = 0;

    private int levelWidth = 0;
    private int levelHeight = 0;

    // Температура
    private int temperature;
    private int tempHeight;

    // Константы
    // Отступ элементов
    private final static int innerPadding = 5;

    public ThermometerView(Context context) {
        super(context);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        init();
    }

    public ThermometerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        init();
    }

    // Инициализация атрибутов пользовательского элемента из xml
    private void initAttr(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.thermometerView, 0, 0);

        thermometerColor = typedArray.getColor(R.styleable.thermometerView_thermometer_color, Color.GRAY);
        levelColor = typedArray.getColor(R.styleable.thermometerView_level_color, Color.RED);
        scaleColor = typedArray.getColor(R.styleable.thermometerView_scale_color, Color.WHITE);
        temperature = typedArray.getInteger(R.styleable.thermometerView_temperature, 0);

        typedArray.recycle();
    }

    // Начальная инициализация полей класса
    private void init() {
        thermometerPaint = new Paint();
        thermometerPaint.setColor(thermometerColor);
        thermometerPaint.setStyle(Paint.Style.FILL);
        temperaturePaint = new Paint();
        temperaturePaint.setColor(levelColor);
        temperaturePaint.setStyle(Paint.Style.FILL);
        scalePaint = new Paint();
        scalePaint.setColor(scaleColor);
        scalePaint.setStyle(Paint.Style.FILL);
        zeroLevelPaint = new Paint();
        zeroLevelPaint.setColor(Color.BLACK);
        zeroLevelPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Получаем реальные ширину и высоту
        width = w - getPaddingLeft() - getPaddingRight();
        height = h - getPaddingTop() - getPaddingBottom();
        levelWidth = (int) (0.2 * width);
        levelHeight = (int) (0.8 * height);
        round = width / 2;


        // Отрисовка термометра
        headRectangle.set(0,
                0,
                width,
                round * 2);

        thermometertRectangle.set(0,
                round,
                width,
                height);


        scaleRectangle.set(width / 2 - levelWidth / 2,
                height / 2 - levelHeight / 2,
                width / 2 + levelWidth / 2,
                height / 2 + levelHeight / 2);

        zeroLevelRectangle.set(width / 2 - levelWidth / 2,
                height / 2 - 2,
                width / 2 + levelWidth / 2,
                height / 2 + 2);

        tempHeight = levelHeight - 2 * innerPadding;
        setTemperatureRectangle();
    }

    //     Вызывается, когда надо нарисовать элемент
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(thermometertRectangle, thermometerPaint);
        canvas.drawRoundRect(headRectangle, round, round, thermometerPaint);
        canvas.drawRect(scaleRectangle, scalePaint);
        canvas.drawRect(temperatureRectangle, temperaturePaint);
        canvas.drawRect(zeroLevelRectangle, zeroLevelPaint);

    }

    private void setTemperatureRectangle() {
        temperatureRectangle.set(width / 2 - levelWidth / 2 + innerPadding,
                (int) ((height / 2 - levelHeight / 2 + innerPadding) + (1 + (double) (-50 - temperature) / 100) * tempHeight),
                width / 2 + levelWidth / 2 - innerPadding,
                height / 2 + levelHeight / 2 - innerPadding);
    }

    public void displayTemp(int temperature) {
        this.temperature = temperature;
        setTemperatureRectangle();
        invalidate();
    }
}
