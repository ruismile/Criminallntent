package com.hanrui.android.criminallntent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * @author
 * @version 1.0
 * @date 2017/5/23 0023
 */

public class CrimeListFragment extends Fragment{
    
    private static final String SAVED_SUBTITLE_VISIBLE="subtitle";
    
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    //新增跟踪记录子标题状态的成员变量
    private boolean mSubtitleVisible;
    
    //让FragmentManager知道CrimeListFragment需接收选项菜单方法回调
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        //创建RecyclerView视图
        mCrimeRecyclerView=(RecyclerView)view.findViewById(R.id.crime_recycler_view);
        //把RecyclerView视图转交给LayoutManager
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        if(savedInstanceState!=null){
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        
        //将Adapter和RecycleView关联
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    
    //保存设备旋转后子标题状态值


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    //创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        //点击Show Subtitle后，更新子标题
        MenuItem subtitleItem=menu.findItem(R.id.menu_item_show_subtitle);
        if(mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }
    //响应菜单点击事件
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_item_crime:
                Crime crime=new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent=CrimePagerActivity.newIntent(getActivity(),crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //显示子标题的记录数
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeSize=crimeLab.getCrimes().size();
        String subtitle=getResources().getQuantityString(R.plurals.subtitle_plural,crimeSize,crimeSize);
        
        if(!mSubtitleVisible){
            subtitle=null;
        }

        AppCompatActivity activity=(AppCompatActivity)getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    //创建CrimeAdapter,然后设置给RecycleView，将Adapter和RecycleView关联
    private void updateUI(){
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        List<Crime>crimes=crimeLab.getCrimes();
        
        if(mAdapter==null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            //更改明细内容后通过这个方法刷新列表
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }
    
    //定义ViewHolder内部类
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Crime mCrime;
        
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        
        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            
            mTitleTextView=(TextView)itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView=(TextView)itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox=(CheckBox)itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }
        public void bindCrime(Crime crime){
            mCrime=crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent=CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    //创建adapter内部类
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime>mCrimes;
        public CrimeAdapter(List<Crime>crimes){
            mCrimes=crimes;
        }

        //创建View视图，封装到ViewHolder中
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            //显示在列表的资源布局文件
            View view=layoutInflater.inflate(R.layout.list_item_crime,parent,false);
            return new CrimeHolder(view);
        }
        //把ViewHolder的View视图和模型层(Crime)数据绑定起来
        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime=mCrimes.get(position);
            holder.bindCrime(crime);
        }
        
        //数组列表包含多少对象
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
        
        public void setCrimes(List<Crime>crimes){
            mCrimes=crimes;
        }
    }
}
