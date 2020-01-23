package com.sinhro.angles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sinhro.angles.Logic.AFormula;
import com.sinhro.angles.Logic.MyConsumer;
import com.sinhro.angles.Logic.Utils;


public class RotationToolHolderFragment extends Fragment {

    public static RotationToolHolderFragment newInstance() {
        RotationToolHolderFragment fragment = new RotationToolHolderFragment();
        return fragment;
    }

    private EditText D_editText;
    private EditText d_editText;
    private EditText L_editText;
    private TextView alpha_TextView;

    Formula formula;

    private void findElems(View v){
        D_editText = v.findViewById(R.id.editText_D);
        d_editText = v.findViewById(R.id.editText_d);
        L_editText = v.findViewById(R.id.editText_L);
        alpha_TextView = v.findViewById(R.id.textView_alpha_RotAngToolHold);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View body =inflater.inflate(R.layout.rotation_tool_holder_fragment, container, false);
        findElems(body);
        TextView title = body.findViewById(R.id.title);
        title.setText(R.string.title_rotationToolHolder);


        formula = new Formula(new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                alpha_TextView.setText(String.valueOf(aFloat));
            }
        });

        Utils.setTextChangedListener(d_editText, new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                formula.setd(aFloat);
            }
        });
        Utils.setTextChangedListener(D_editText, new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                formula.setD(aFloat);
            }
        });
        Utils.setTextChangedListener(L_editText, new MyConsumer<Float>() {
            @Override
            public void accept(Float aFloat) {
                formula.setL(aFloat);
            }
        });

        return body;
    }


    class Formula extends AFormula{
        Float D,d,L;

        public Formula(MyConsumer<Float> onAnswerReady) {
            super(onAnswerReady);
        }

        @Override
        public void initArguments() {
            d = null;
            D = null;
            L = null;
        }

        @Override
        public Float calcFormula() {
            return ((float) Math.atan(Math.toRadians(((D - d) / 2) / L)));
        }

        @Override
        public boolean isAllArgumentsReady() {
            return D != null && d != null && L!= null;
        }

        public void setD(Float d) {
            D = d;
            checkIsReady();
        }

        public void setd(Float d) {
            this.d = d;
            checkIsReady();
        }

        public void setL(Float l) {
            L = l;
            checkIsReady();
        }
    }
}
