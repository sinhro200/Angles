package com.sinhro.angles.Logic;

public abstract class AFormula {
    MyConsumer<Float> onAnswerReady;

    public AFormula(MyConsumer<Float> onAnswerReady) {
        this.onAnswerReady = onAnswerReady;
        initArguments();
    }

    protected void checkIsReady(){
        if (isAllArgumentsReady())
            onAnswerReady.accept(calcFormula());
    }

    public abstract void initArguments();

    public abstract Float calcFormula();

    public abstract boolean isAllArgumentsReady();
}
