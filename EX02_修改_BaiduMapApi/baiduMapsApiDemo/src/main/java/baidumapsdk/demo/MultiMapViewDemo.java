package baidumapsdk.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;

/**
 * 在一个Activity中展示多个地图
 */
public class MultiMapViewDemo extends FragmentActivity {

	private static final LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
	private static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
	private static final LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
	private static final LatLng GEO_SHENGZHENG = new LatLng(22.560, 114.064);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multimap);
		initMap();
	}

	/**
	 * 初始化Map
	 */
	private void initMap() {
		MapStatusUpdate u1 = MapStatusUpdateFactory.newLatLng(GEO_BEIJING);
		SupportMapFragment map1 = (SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map1));
		map1.getBaiduMap().setMapStatus(u1);

		MapStatusUpdate u2 = MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
		SupportMapFragment map2 = (SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map2));
		map2.getBaiduMap().setMapStatus(u2);

		MapStatusUpdate u3 = MapStatusUpdateFactory.newLatLng(GEO_GUANGZHOU);
		SupportMapFragment map3 = (SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map3));
		map3.getBaiduMap().setMapStatus(u3);

		MapStatusUpdate u4 = MapStatusUpdateFactory.newLatLng(GEO_SHENGZHENG);
		SupportMapFragment map4 = (SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map4));
		map4.getBaiduMap().setMapStatus(u4);
	}

}
