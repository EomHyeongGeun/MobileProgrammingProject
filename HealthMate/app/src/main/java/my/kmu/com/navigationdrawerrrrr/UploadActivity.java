package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by eomhyeong-geun on 2016. 12. 4..
 */

public class UploadActivity extends Activity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private int id_view;
    private ImageView iv_UserPhoto;
    private Uri mlmageCaptureUri;
    private String absolutePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);

        iv_UserPhoto = (ImageView)findViewById(R.id.user_image);
        Button btn_agreeJoin = (Button)findViewById(R.id.btn_UploadPicture);

    }

    public void pictureClick(View v){
        id_view = v.getId();
        if(v.getId() == R.id.btn_signupfinish){

        }
        // 사진 선택 클릭시
        else if(v.getId() == R.id.btn_UploadPicture){
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this).setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진 촬영", cameraListener)
                    .setNeutralButton("앨범 선택", albumListener)
                    .setNegativeButton("취소", cancelListener).show();
        } // else if문 종료

    } // onCreate 종료

    // 카메라에서 사진촬영 (카메라 촬영 후 이미지 가져오기)
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mlmageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mlmageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction(){
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode){
            case PICK_FROM_ALBUM:
            {
                mlmageCaptureUri = data.getData();
                Log.d("HealthMate", mlmageCaptureUri.getPath().toString());
            }
            case PICK_FROM_CAMERA:
            {
                // 일단 이미지를 가져오고 이후에 리사이즈할 이미지의 크기를 결정
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mlmageCaptureUri, "image/*");

                // CROP할 이미지를 200*200의 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP박스의 x축 비율
                intent.putExtra("aspectX", 1); // CROP박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE); // CROP_FROM_IMAGE case문으로 이동
                break;
            }
            case CROP_FROM_IMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받고 이미지 뷰에 이미지를 보여준다거나 부가적 작업이후 임시파일 삭제
                if(resultCode != RESULT_OK){
                    return;
                }

                final Bundle extras = data.getExtras();
                //Crop된 이미지를 저장하기 위한 File 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        +"/HealthMate/" + System.currentTimeMillis() + ".jpg";

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data"); // CROP된 Bitmap
                    iv_UserPhoto.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다
                    absolutePath = filePath;
                    break;
                }

                // 임시 파일 삭제
                File f = new File(mlmageCaptureUri.getPath());
                if(f.exists()){
                    f.delete();
                }
            }
        }
    } // onActivityResult 종료

    // Bitmap 저장
    private void storeCropImage(Bitmap bitmap, String filePath){
        // HealthMate 폴더를 생성하여 이미지를 저장하는 방식
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/HealthMate";
        File directory_HealthMate = new File(dirPath);

        if(!directory_HealthMate.exists()) //HealthMate 디렉터리에 폴더가 없다면 폴더를 만들어라
            directory_HealthMate.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try{
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
