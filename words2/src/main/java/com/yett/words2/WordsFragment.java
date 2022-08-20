package com.yett.words2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private final static String VIEW_TYPE_SHP = "view_type_shp";
    private final static String IS_USING_CARD_VIEW = "is_using_card_view";

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
        setHasOptionsMenu(true);//默认是不显示菜单的
    }
    //添加菜单到该fragment，并给菜单的searchView添加搜索事件
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
        //由于搜索框默认很长，会把导航栏的标题挤掉，所以将其设置的短一点
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(750);
        //给搜索框添加事件，当搜索框文本变化时触发事件
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredWords.removeObservers(requireActivity());//清除先前设置的监听
                filteredWords = wordViewModel.getFilteredWordsLive(newText.trim());
                filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
                    @Override
                    public void onChanged(List<Word> words) {
                        int temp = myAdapterNormal.getItemCount();
//                        myAdapterCard.setAllWords(words);
//                        myAdapterNormal.setAllWords(words);
                        if (temp!=words.size()){
//                            myAdapterNormal.notifyDataSetChanged();
//                            myAdapterCard.notifyDataSetChanged();
                            myAdapterNormal.submitList(words);
                            myAdapterCard.submitList(words);
                        }
                    }
                });

                return true;//只要这里处理完成就行，不需要向下继续提交
            }
        });
    }

    //给菜单中的菜单项添加事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.clearData:
                //删除数据需要进行提示，防止数据是误删
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
                boolean isUsingCardView = shp.getBoolean(IS_USING_CARD_VIEW,false);
                SharedPreferences.Editor editor = shp.edit();
                if (isUsingCardView){
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
        View view = inflater.inflate(R.layout.fragment_words,container,false);
        this.recyclerView = view.findViewById(R.id.recyclerView);
        this.floatingActionButton = view.findViewById(R.id.floatingActionButton);
        this.wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        this.myAdapterCard = new MyAdapter(wordViewModel,true);
        this.myAdapterNormal = new MyAdapter(wordViewModel,false);

        this.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //在动画结束刷新序号
        this.recyclerView.setItemAnimator(new DefaultItemAnimator(){
            @Override
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager!=null){
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i=firstPosition;i<=lastPosition;i++){
                        MyAdapter.MyViewHolder holder = (MyAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder!=null){
                            holder.textViewNumber.setText(String.valueOf(i+1));
                        }
                    }
                }
            }
        });

        SharedPreferences shp = requireActivity().getSharedPreferences(VIEW_TYPE_SHP,Context.MODE_PRIVATE);
        boolean isUsingCardView = shp.getBoolean(IS_USING_CARD_VIEW,false);
        //this.recyclerView.setAdapter(this.myAdapterNormal);
        if (isUsingCardView){
            recyclerView.setAdapter(myAdapterCard);
        }else {
            recyclerView.setAdapter(myAdapterNormal);
        }
        //监听数据的变化的
        this.filteredWords = this.wordViewModel.getAllWordsLive();
        this.filteredWords.observe(requireActivity(), new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                int temp = myAdapterNormal.getItemCount();
//                myAdapterNormal.setAllWords(words);
//                myAdapterCard.setAllWords(words);
                if (temp!=words.size()){
                    recyclerView.smoothScrollBy(0,-200);//向下滑动200像素
//                    myAdapterNormal.notifyDataSetChanged();
//                    myAdapterCard.notifyDataSetChanged();
                    myAdapterNormal.submitList(words);
                    myAdapterCard.submitList(words);
                }
            }
        });

        //添加导航的，点击跳转到添加单词界面
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });
        return view;
    }
}