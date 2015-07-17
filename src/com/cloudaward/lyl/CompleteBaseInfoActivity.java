package com.cloudaward.lyl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.cloudaward.lyl.consts.UrlConsts;
import com.cloudaward.lyl.network.LylJSONObject;
import com.cloudaward.lyl.network.LylJsonObjectRequest;
import com.cloudaward.lyl.network.MultipartRequest;
import com.cloudaward.lyl.utils.ActionBarUtils;
import com.cloudaward.lyl.utils.DimenUtils;
import com.cloudaward.lyl.utils.ImageUtils;


@SuppressWarnings("deprecation")
public class CompleteBaseInfoActivity extends ActionBarActivity implements OnClickListener {

  protected static final String TAG = CompleteBaseInfoActivity.class.getSimpleName();

  private static final String LYL_TMP_AVATAR_FILE = "lyl_tmp_avatar";
  private static int mAvatarSize = 96; // default

  private static final int PICK_FROM_CAMERA = 1;
  private static final int CROP_FROM_CAMERA = 2;
  private static final int PICK_FROM_FILE = 3;


  private ImageView mAvatarImageView;
  private Uri mImageCaptureUri;
  private AlertDialog mCaptureDialog;

  private String uploadedAvatarUrl;

  private EditText mNicknameEditText;

  private RadioGroup mGenderRadioGroup;

  private Button mCompleteButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_complete_base_info);
    ActionBarUtils.initGeneralActionBar(this);
    mAvatarImageView = (ImageView) findViewById(R.id.iv_avatar);
    mAvatarImageView.setOnClickListener(this);
    initCaptureDialog();

    mNicknameEditText = (EditText) findViewById(R.id.et_nickname);

    mGenderRadioGroup = (RadioGroup) findViewById(R.id.rg_gender);

    mCompleteButton = (Button) findViewById(R.id.btn_complete);
    mCompleteButton.setOnClickListener(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    SharedPreferences prefs = getAvatarSharedPrefs();
    String avatarUrl = prefs.getString("avatarUrl", "");
    if (avatarUrl != null) {
      displayAvatar(avatarUrl);
    }
    // @formatter:off
  }

  private SharedPreferences getAvatarSharedPrefs() {
    SharedPreferences prefs = getSharedPreferences("avatar", MODE_PRIVATE);
    return prefs;
  }

  private void displayAvatar(String url) {
    ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
      @Override
      public void onResponse(Bitmap bitmap) {
        RoundedBitmapDrawable drawable = ImageUtils.getCircleBitmap(getApplicationContext(), bitmap);
        mAvatarImageView.setImageDrawable(drawable);
      }
    }, 0, 0, null, new Response.ErrorListener() {
      public void onErrorResponse(VolleyError error) {
        mAvatarImageView.setImageResource(R.drawable.ic_avatar);
      }
    });
    MainApplication.getInstance().getRequestQueue().add(request);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    // getMenuInflater().inflate(R.menu.complete_base_info, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    if (v == mAvatarImageView) {
      mCaptureDialog.getWindow().setGravity(Gravity.BOTTOM);
      mCaptureDialog.show();
    }
    else if(v == mCompleteButton) {
      if(TextUtils.isEmpty(mNicknameEditText.getEditableText())) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.nickname), Toast.LENGTH_SHORT).show();
        return;
      }
      if(uploadedAvatarUrl == null) {
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.pls_upload_avatar), Toast.LENGTH_SHORT).show();
        return;
      }
      int checkedId = mGenderRadioGroup.getCheckedRadioButtonId();
      int gender = checkedId == 0 ? 1: 2;
      LylJSONObject jsonRequest = null;
      JSONObject data = new JSONObject();
      try {
        data.put("head", uploadedAvatarUrl);
        data.put("sex", gender);
        data.put("nickName", mNicknameEditText.getText().toString());
        jsonRequest = new LylJSONObject(this, data);
      } catch (JSONException e) {
        Log.e(TAG, e.getMessage());
        return;
      }
      // @formatter:off
      LylJsonObjectRequest request = new LylJsonObjectRequest(Method.POST, UrlConsts.completeBaseInfoUrl, jsonRequest, 
        new Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            if(response != null) {
              Log.i(TAG, response.toString());
              int code = -1;
              try {
                code = response.getInt("code");
              } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
              }
              if(code == 0) {
                Intent intent = new Intent();
                intent.setClass(CompleteBaseInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
              }
              Toast.makeText(getApplicationContext(), getResources().getString(R.string.pls_upload_avatar), Toast.LENGTH_SHORT).show();
            }
          }
        }, 
        new ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            
          }
        });
      MainApplication.getInstance().getRequestQueue().add(request);
      // @formatter:on
    }
  }

  private void initCaptureDialog() {
    // @formatter:off
    final String[] items = new String[] {
        getResources().getString(R.string.take_from_camera), 
        getResources().getString(R.string.select_from_gallery), 
    };
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int item) {
        if (item == 0) {
          Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          File externalStorageDirectory = Environment.getExternalStorageDirectory();
          String filename = LYL_TMP_AVATAR_FILE + System.currentTimeMillis() + ".jpg";
          mImageCaptureUri = Uri.fromFile(new File(externalStorageDirectory, filename));
          intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
          intent.putExtra("return-data", true);
          try {
            startActivityForResult(intent, PICK_FROM_CAMERA);
          } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage());
          }
        } else {
          Intent intent = new Intent();
          intent.setType("image/*");
          intent.setAction(Intent.ACTION_GET_CONTENT);
          startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
        }
      }
    });
    // @formatter:on
    mCaptureDialog = builder.create();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK)
      return;
    switch (requestCode) {
      case PICK_FROM_CAMERA:
        doCrop();
        break;
      case PICK_FROM_FILE:
        mImageCaptureUri = data.getData();
        doCrop();
        break;
      case CROP_FROM_CAMERA:
        Bundle extras = data.getExtras();
        File f = new File(mImageCaptureUri.getPath());
        if (extras != null) {
          Bitmap bitmap = extras.getParcelable("data");
          RoundedBitmapDrawable drawable = ImageUtils.getCircleBitmap(this, bitmap);
          mAvatarImageView.setImageDrawable(drawable);
          uploadAvatar(drawable.getBitmap());
        }
        if (f.exists()) {
          f.delete();
        }
        break;
    }
  }

  private void uploadAvatar(Bitmap bitmap) {
    // @formatter:off
    Map<String, String> params = new HashMap<String, String>();
    params.put("bizType", "head");
    MultipartRequest request = new MultipartRequest(UrlConsts.uploadUrl, 
        new Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            if(response != null) {
              Log.i(TAG, response.toString());
            }
            int code = -1;
            try {
              code = response.getInt("code");
            } catch (JSONException e) {
              Log.e(TAG, e.getMessage());
            }
            if(code == 0) {
              uploadAvatarOK(response);
            }
          }
        }, 
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.i(TAG, error.getMessage());
          }
        }, bitmap, params);
    // @formatter:on
    MainApplication.getInstance().getRequestQueue().add(request);
  }

  private void uploadAvatarOK(JSONObject response) {
    JSONObject jsonObject = null;
    try {
      jsonObject = response.getJSONObject("data");
      if (jsonObject != null) {
        String wholeUrl = jsonObject.getString("wholeUrl");
        uploadedAvatarUrl = wholeUrl;
        SharedPreferences prefs = getAvatarSharedPrefs();
        Editor editor = prefs.edit();
        editor.putString("avatarUrl", wholeUrl);
        editor.commit();
        displayAvatar(wholeUrl);
      }
    } catch (JSONException e) {
      Log.e(TAG, e.getMessage());
    }
  }

  private class CropOption {
    public CharSequence title;
    public Drawable icon;
    public Intent appIntent;
  }

  private class CropOptionAdapter extends ArrayAdapter<CropOption> {
    private ArrayList<CropOption> mOptions;
    private LayoutInflater mInflater;

    public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
      super(context, R.layout.list_item_crop_option, options);
      mOptions = options;
      mInflater = LayoutInflater.from(context);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup group) {
      ViewHolder holder = null;
      if (convertView == null) {
        convertView = mInflater.inflate(R.layout.list_item_crop_option, null);
        holder = new ViewHolder();
        holder.mTitle = ((TextView) convertView.findViewById(R.id.tv_name));
        holder.mIcon = ((ImageView) convertView.findViewById(R.id.iv_icon));
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      CropOption item = mOptions.get(position);
      if (item != null) {
        holder.mIcon.setImageDrawable(item.icon);
        holder.mTitle.setText(item.title);
      }
      return convertView;
    }

    private class ViewHolder {
      ImageView mIcon;
      TextView mTitle;
    }
  }

  private void doCrop() {
    // @formatter:off
    final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setType("image/*");
    List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
    int size = list.size();
    if (size <= 0) {
      Toast.makeText(this, getResources().getString(R.string.without_capture_app), Toast.LENGTH_SHORT).show();
      return;
    } else {
      int px = (int) DimenUtils.convertDpToPixel(mAvatarSize, this);
      intent.setData(mImageCaptureUri);
      intent.putExtra("outputX", px);
      intent.putExtra("outputY", px);
      intent.putExtra("aspectX", 1);
      intent.putExtra("aspectY", 1);
      intent.putExtra("scale", true);
      intent.putExtra("return-data", true);
      if (size == 1) {
        Intent i = new Intent(intent);
        ResolveInfo res = list.get(0);
        i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
        startActivityForResult(i, CROP_FROM_CAMERA);
      } else {
        for (ResolveInfo res : list) {
          final CropOption co = new CropOption();
          co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
          co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
          co.appIntent = new Intent(intent);
          co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
          cropOptions.add(co);
        }
        CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
          }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialog) {
            if (mImageCaptureUri != null) {
              getContentResolver().delete(mImageCaptureUri, null, null);
              mImageCaptureUri = null;
            }
          }
        });
        AlertDialog alert = builder.create();
        alert.show();
      }
    }
    //@formatter:off
  } // end doCrop
}
