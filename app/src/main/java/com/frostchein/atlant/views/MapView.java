package com.frostchein.atlant.views;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.frostchein.atlant.R;
import com.frostchein.atlant.utils.DimensUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapView extends RelativeLayout implements OnMapReadyCallback {

  private GoogleMap googleMap;
  private Geocoder geocoder;
  private ImageView imTransparent;
  private int drawableIcon = 0;
  private double var1 = 0;
  private double var2 = 0;
  private float zoom = 10;
  private CallBack callBack;

  public interface CallBack {

    void getAddress(String address);
  }

  public void setCallBack(CallBack callBack) {
    this.callBack = callBack;
  }

  public MapView(Context context) {
    super(context);
    initView(context);
  }

  public MapView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MapView);
    var1 = typedArray.getFloat(R.styleable.MapView_mv_var1, (float) var1);
    var2 = typedArray.getFloat(R.styleable.MapView_mv_var2, (float) var2);
    zoom = typedArray.getFloat(R.styleable.MapView_mv_zoom, zoom);
    drawableIcon = typedArray.getResourceId(R.styleable.MapView_mv_icon, drawableIcon);
    typedArray.recycle();
    initView(context);
  }

  private void initView(Context context) {
    setWillNotDraw(false);
    View view = inflate(context, R.layout.view_map, this);
    Activity activity = (Activity) context;
    MapFragment mapFragment = (MapFragment) activity.getFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    geocoder = new Geocoder(context, Locale.ENGLISH);
    imTransparent = view.findViewById(R.id.map_image_transparent);
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    this.googleMap = googleMap;
    update();
  }


  public void setLatLng(double var1, double var2) {
    this.var1 = var1;
    this.var2 = var2;
    update();
  }

  public void setIcon(@DrawableRes int drawable) {
    this.drawableIcon = drawable;
    update();
  }

  public void disableNestedScrollView(final NestedScrollView scrollView) {
    imTransparent.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
          case MotionEvent.ACTION_DOWN:
            scrollView.requestDisallowInterceptTouchEvent(true);
            return false;
          case MotionEvent.ACTION_UP:
            scrollView.requestDisallowInterceptTouchEvent(false);
            return true;
          case MotionEvent.ACTION_MOVE:
            scrollView.requestDisallowInterceptTouchEvent(true);
            return false;
          default:
            return true;
        }
      }
    });
  }

  private void update() {
    if (googleMap != null) {
      LatLng latLng = new LatLng(var1, var2);
      if (drawableIcon != 0) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(drawableIcon);
        googleMap.addMarker(new MarkerOptions().position(latLng).icon(icon));
      }
      googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
      googleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
      new AsyncTaskAddress().execute();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    addTriangleToUp(canvas);
    super.onDraw(canvas);
  }

  private void addTriangleToUp(Canvas canvas) {
    Path path1 = new Path();
    Paint paint = new Paint();
    paint.setColor(Color.RED);

    int x = 32;
    RectF rect = new RectF(0, DimensUtils.dpToPx(getContext(), 16), this.getWidth(), this.getHeight());

    path1.moveTo(DimensUtils.dpToPx(getContext(), 16 + x), 0);
    path1.lineTo(DimensUtils.dpToPx(getContext(), 32 + x), DimensUtils.dpToPx(getContext(), 16));
    path1.lineTo(DimensUtils.dpToPx(getContext(), 0 + x), DimensUtils.dpToPx(getContext(), 16));
    path1.close();

    path1.addRect(rect, Direction.CCW);
    canvas.clipPath(path1);
  }

  private class AsyncTaskAddress extends AsyncTask<Void, Void, Void> {

    private List<Address> addressList;

    @Override
    protected Void doInBackground(Void... voids) {
      try {
        addressList = geocoder.getFromLocation(var1, var2, 1);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      if (callBack != null && addressList != null && addressList.size() > 0) {
        callBack.getAddress(addressList.get(0).getAddressLine(0));
      }
    }
  }
}
