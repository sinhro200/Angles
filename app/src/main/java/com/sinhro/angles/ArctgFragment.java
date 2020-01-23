package com.sinhro.angles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sinhro.angles.Logic.AFormula;
import com.sinhro.angles.Logic.MyConsumer;
import com.sinhro.angles.Logic.Utils;

public class ArctgFragment extends Fragment {
    public static ArctgFragment newInstance() {
        ArctgFragment fragment = new ArctgFragment();
        return fragment;
    }

    EditText a_editText;
    EditText b_editText;
    TextView alpha_textView;
    TextView beta_textView;

    Formula formula;

    private void findElems(View v){
        a_editText = v.findViewById(R.id.editText_a);
        b_editText = v.findViewById(R.id.editText_b);
        alpha_textView = v.findViewById(R.id.textView_alpha);
        beta_textView = v.findViewById(R.id.textView_beta);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View body =inflater.inflate(R.layout.arctg_fragment, container, false);
        findElems(body);
        TextView title = body.findViewById(R.id.title);
        title.setText(R.string.title_arctg);

        formula = new Formula(new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                if (aFloat == null) {
                    alpha_textView.setText("");
                    beta_textView.setText("");
                }
                else {
                    alpha_textView.setText(String.valueOf(aFloat));
                    alpha_textView.append(getResources().getText(R.string.degree_symbol));
                    beta_textView.setText(String.valueOf(90 - aFloat));
                    beta_textView.append(getResources().getText(R.string.degree_symbol));
                }
            }
        });

        Utils.setTextChangedListener(a_editText, new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                formula.setA(aFloat);
            }
        });
        Utils.setTextChangedListener(b_editText, new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                formula.setB(aFloat);
            }
        });

        return body;
    }

    private class Formula extends AFormula{
        private Float a,b;
        public Formula(MyConsumer<Float> onAnswerReady) {
            super(onAnswerReady);
        }

        @Override
        public void initArguments() {
            a = null;
            b = null;
        }

        @Override
        public Float calcFormula() {
            return ((float) Math.toDegrees(Math.atan(a / b)));
        }

        @Override
        public boolean isAllArgumentsReady() {
            return a != null && b != null;
        }

        public void setA(Float a) {
            this.a = a;
            checkIsReady();
        }

        public void setB(Float b) {
            this.b = b;
            checkIsReady();
        }
    }
}
