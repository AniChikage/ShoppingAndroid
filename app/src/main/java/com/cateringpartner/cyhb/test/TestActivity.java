package com.cateringpartner.cyhb.test;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.cateringpartner.cyhb.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by AniChikage on 2017/9/10.
 */

public class TestActivity extends Activity {

    private Button btn;
    private String imgPath="";
    private MaterialRatingBar materialRatingBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
        setContentView(R.layout.testactivity);

        materialRatingBar = (MaterialRatingBar)findViewById(R.id.ratingbar);
        btn = (Button)findViewById(R.id.btn);
        materialRatingBar.setNumStars(5);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TestActivity.this,materialRatingBar.getRating()+"",Toast.LENGTH_SHORT).show();

                // 必须异步调用

            }
        });
    }

    private void uploadImg(){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imgPath = picturePath;
            //Toast.makeText(getApplicationContext(),imgPath+","+util.getGLOBALUSERID()+","+materialRatingBar.getNumStars()+","+et_comment.getText().toString().trim().toString(),Toast.LENGTH_SHORT).show();
            cursor.close();
        }
    }
}
