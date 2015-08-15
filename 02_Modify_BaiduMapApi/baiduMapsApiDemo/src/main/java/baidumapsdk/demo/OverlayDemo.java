package baidumapsdk.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

/**
 * 演示覆盖物的用法
 */
public class OverlayDemo extends Activity {

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private InfoWindow mInfoWindow;

	private ArrayList<Marker> mMarkerList = new ArrayList<Marker>();

	// 初始化全局 bitmap 信息，不用时及时 recycle
	private ArrayList<BitmapDescriptor> mBitmapList= new ArrayList<BitmapDescriptor>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overlay);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		initOverlay();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.popup);
				OnInfoWindowClickListener listener = null;
				if (marker == mMarkerList.get(0) || marker == mMarkerList.get(3)) {
					button.setText("更改位置");
					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {
							LatLng ll = marker.getPosition();
							LatLng llNew = new LatLng(ll.latitude + 0.005,
									ll.longitude + 0.005);
							marker.setPosition(llNew);
							mBaiduMap.hideInfoWindow();
						}
					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerList.get(1)) {
					button.setText("更改图标");
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							marker.setIcon(mBitmapList.get(4));
							mBaiduMap.hideInfoWindow();
						}
					});
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(button, ll, -47);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerList.get(2)) {
					button.setText("删除");
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							marker.remove();
							mBaiduMap.hideInfoWindow();
						}
					});
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(button, ll, -47);
					mBaiduMap.showInfoWindow(mInfoWindow);
				}
				return true;
			}
		});
	}



	private MarkerOptions addOverlay(double lat, double lng, int resID, int zIndex) {
		LatLng llA = new LatLng(lat, lng);
		BitmapDescriptor bdA = BitmapDescriptorFactory
				.fromResource(resID);
		mBitmapList.add(bdA);
		MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
				.zIndex(zIndex).draggable(true);
		mMarkerList.add((Marker) mBaiduMap.addOverlay(ooA));
		return ooA;
	}

	public void initOverlay() {
		// add marker overlay
		addOverlay(39.963175, 116.400244, R.drawable.icon_marka, 9);
		addOverlay(39.942821, 116.369199, R.drawable.icon_markb, 5);
		MarkerOptions ooC = (MarkerOptions) addOverlay(39.939723, 116.425541, R.drawable.icon_markc, 7);
		MarkerOptions ooD = (MarkerOptions) addOverlay(39.906965, 116.401394, R.drawable.icon_markd, 0);
		ooC.perspective(false).anchor(0.5f, 0.5f).rotate(30);
		ooD.period(10);


//		ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
//		giflist.add(bdA);
//		giflist.add(bdB);
//		giflist.add(bdC);

		BitmapDescriptor bd = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		mBitmapList.add(bd);
		// add ground overlay
		LatLng southwest = new LatLng(39.92235, 116.380338);
		LatLng northeast = new LatLng(39.947246, 116.414977);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
				.include(southwest).build();

		BitmapDescriptor bdGround = BitmapDescriptorFactory
				.fromResource(R.drawable.ground_overlay);

		mBitmapList.add(bdGround);
		OverlayOptions ooGround = new GroundOverlayOptions()
				.positionFromBounds(bounds).image(bdGround).transparency(0.8f);
		mBaiduMap.addOverlay(ooGround);

		MapStatusUpdate u = MapStatusUpdateFactory
				.newLatLng(bounds.getCenter());
		mBaiduMap.setMapStatus(u);

		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			public void onMarkerDrag(Marker marker) {
			}

			public void onMarkerDragEnd(Marker marker) {
				Toast.makeText(
						OverlayDemo.this,
						"拖拽结束，新位置：" + marker.getPosition().latitude + ", "
								+ marker.getPosition().longitude,
						Toast.LENGTH_LONG).show();
			}

			public void onMarkerDragStart(Marker marker) {
			}
		});
	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay();
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// 回收 bitmap 资源
		for (int i = 0; i < mBitmapList.size(); i++) {
			mBitmapList.get(i).recycle();
		}
//		bdA.recycle();
//		bdB.recycle();
//		bdC.recycle();
//		bdD.recycle();
//		bd.recycle();
//		bdGround.recycle();
	}

}
