package com.smm.payCenter.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



//import com.google.gson.Gson;

public class LayerImg {

		public String name; //图片名
		public int pid; //图片id
		public String src;//原图地址
		public String thumb; //缩略图地址
		public int[] area = new int[2];//原图宽高
		
		public LayerImg(){}
		public LayerImg(String name, int pid, String src, String thumb, int[] area){
			this.name = name;
			this.pid=pid;
			this.src=src;
			this.thumb = thumb;
			this.area=area;
		}
		/**
		 * @param status //请求的状态，1表示成功，其它表示失败
		 * @param msg    //状态提示语
		 * @param title  //相册标题
		 * @param id     //相册id 
		 * @param start  //初始显示的图片序号，默认0
		 * @param list   //相册包含的图片，数组格式
		 * @return json字符串
		 */
		public String buildLayerImg(int status, String msg, String title, int id, int start, List<LayerImg> list){
			HashMap<String,Object> hm = new HashMap<String, Object>();
			
			hm.put("status", status);
			hm.put("msg", msg);
			hm.put("title", title);
			hm.put("id", id);
			hm.put("start", start);
			hm.put("data", list);
			String json = "{}";
			
			try {
				json = new ObjectMapper().writeValueAsString(hm);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			return json;
		}
		
		public static void main(String[] args) {
			List<LayerImg> list = new ArrayList<LayerImg>();
			for(int i = 0; i < 3; i++){
				LayerImg img = new LayerImg();
				img.pid = 1;
				img.src="我";
				img.thumb="dddd";
				list.add(img);
			}
			try {
				System.out.println(new ObjectMapper().writeValueAsString(list));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		
}
