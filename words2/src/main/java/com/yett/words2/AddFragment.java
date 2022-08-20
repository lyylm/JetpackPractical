package com.yett.words2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.internal.TextWatcherAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private EditText editTextEnglilsh,editTextChinese;
    private Button buttonSubmit;
    private WordViewModel wordViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add,container,false);
        this.editTextChinese = view.findViewById(R.id.editTextChinese);
        this.editTextEnglilsh = view.findViewById(R.id.editTextEnglish);
        this.buttonSubmit = view.findViewById(R.id.buttonSubmit);
        this.wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        this.buttonSubmit.setEnabled(false);
        this.editTextEnglilsh.requestFocus();//获取焦点

        //设置文本监视器
        TextWatcher textWatcher = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override//当两个输入框中都有内容时，确定按钮使能
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english = editTextEnglilsh.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                buttonSubmit.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        this.editTextEnglilsh.addTextChangedListener(textWatcher);
        this.editTextChinese.addTextChangedListener(textWatcher);

        this.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = editTextEnglilsh.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                //向数据库添加内容
                wordViewModel.insertWords(new Word(english,chinese));
                //返回单词界面
                NavController controller = Navigation.findNavController(v);
                controller.navigateUp();
            }
        });
        return view;
    }
}