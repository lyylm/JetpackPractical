package com.yett.words;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsFragment extends Fragment {
    private static final String VIEW_TYPE_SHP = "view_type_shp";
    private static final String IS_USING_CARD_VIEW = "is_using_card_view";

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private MyAdapter myAdapterNormal,myAdapterCard;
    private WordViewModel wordViewModel;
    private LiveData<List<Word>> filteredWords;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);//默认是不显示菜单栏
    }

    //添加菜单
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
        //菜单栏的长度太长，导致导航栏的fragment名称被挤掉了
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(750);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override//这个点击确定按钮进行提交
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override//当输入内容变化的时候进行提交，如果返回false会往下继续传递，如果是true则不会继续往下传
            public boolean onQueryTextChange(String newText) {
                //Log.d("lyy", "onQueryTextChange: "+newText);
                filteredWords.removeObservers(requireActivity());//需要先移除
                filteredWords = wordViewModel.findWordsWithPatten(newText.trim());
                filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = myAdapterCard.getItemCount();
                        myAdapterCard.setAllWords(words);
                        myAdapterNormal.setAllWords(words);
                        if (temp!=words.size()){
                            myAdapterNormal.notifyDataSetChanged();
                            myAdapterCard.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
    }

    //菜单按钮功能的实现
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ClearData://清空数据的时候要弹窗进行询问
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("清空数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wordViewModel.deleteAllWords();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.switchViewType:
                SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP, Context.MODE_PRIVATE);
                boolean viewType = shp.getBoolean(IS_USING_CARD_VIEW,false);
                SharedPreferences.Editor editor = shp.edit();
                if (viewType){
                    recyclerView.setAdapter(myAdapterNormal);
                    editor.putBoolean(IS_USING_CARD_VIEW,false);
                }else {
                    recyclerView.setAdapter(myAdapterCard);
                    editor.putBoolean(IS_USING_CARD_VIEW,true);
                }
                editor.apply();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordsFragment newInstance(String param1, String param2) {
        WordsFragment fragment = new WordsFragment();
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
        View view = inflater.inflate(R.layout.fragment_words, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        myAdapterCard = new MyAdapter(true,wordViewModel);
        myAdapterNormal = new MyAdapter(false,wordViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //recyclerView.setAdapter(myAdapterNormal);
        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP,Context.MODE_PRIVATE);
        boolean isUsingCardView = shp.getBoolean(IS_USING_CARD_VIEW,false);
        if (isUsingCardView){
            recyclerView.setAdapter(myAdapterCard);
        }else {
            recyclerView.setAdapter(myAdapterNormal);
        }
        this.filteredWords = wordViewModel.getAllWordsLive();
        this.filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapterCard.getItemCount();
                myAdapterCard.setAllWords(words);
                myAdapterNormal.setAllWords(words);
                if (temp!=words.size()){
                    myAdapterNormal.notifyDataSetChanged();
                    myAdapterCard.notifyDataSetChanged();
                }
            }
        });

        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要添加引用，要注意
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });

        return view;
    }


}





