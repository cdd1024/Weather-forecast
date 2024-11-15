package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.WeatherDetailActivity;
import com.example.myapplication.entity.WeatherInfo;

import java.util.List;
//在列表视图中显示天气信息
public class WeatherListAdapter extends ArrayAdapter<WeatherInfo.ShowapiResBodyBean.WeatherDay> {
    private List<WeatherInfo.ShowapiResBodyBean.WeatherDay> weatherList;
    private Context context;//上下文对象，用于启动新的活动或服务。
    private int resourceId;//列表项的布局资源 ID。
    public WeatherListAdapter(@NonNull Context context, int resource, @NonNull List<WeatherInfo.ShowapiResBodyBean.WeatherDay> list) {
        super(context, resource,  list);
        this.context = context;
        this.weatherList = list;
        this.resourceId=resource;
    }
  //用于创建或重用列表中的每个视图项，并将数据绑定到视图上。
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WeatherInfo.ShowapiResBodyBean.WeatherDay forecastBean = weatherList.get(position);
        View view = LayoutInflater.from(context).inflate(resourceId, parent, false);
//使用 LayoutInflater 将列表项的布局资源 resourceId 转换为视图对象 view。
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        //创建 WeatherViewHolder 对象来保存视图中的子视图。
        //将天气日期格式化为 MM/dd 并设置到对应的文本视图 tvDate 中。
        StringBuilder sb = new StringBuilder(forecastBean.getDay().substring(4));
        sb.insert(2, '/');
        viewHolder.tvDate.setText(sb);
        Glide.with(view).load(forecastBean.getDay_weather_pic()).into(viewHolder.imageWeather);
        //使用 Glide 库加载天气图标到对应的 ImageView 中。
        viewHolder.tvWendu.setText(forecastBean.getDay_air_temperature() + "/" + forecastBean.getNight_air_temperature() + "°C");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherDetailActivity.class);
                intent.putExtra("weatherdetail", forecastBean);
                context.startActivity(intent);
                //启动 WeatherDetailActivity 并传递天气详情数据。
            }

        });
        return view;
    }
    public class WeatherViewHolder{//
        private TextView tvDate;
        private ImageView imageWeather;
        private TextView tvWendu;
        public View itemView;
        public WeatherViewHolder(View itemView){
            this.itemView =itemView;
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            imageWeather = (ImageView) itemView.findViewById(R.id.image_weather);
            tvWendu = (TextView) itemView.findViewById(R.id.tv_wendu);
        }
    }
}
