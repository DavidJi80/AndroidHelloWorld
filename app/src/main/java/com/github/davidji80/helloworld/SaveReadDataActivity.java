package com.github.davidji80.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SaveReadDataActivity extends AppCompatActivity {
    private final String KEY_NAME = "MYKEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedata);
    }


    public void saveData(View view) {
        EditText etWrite = (EditText) findViewById(R.id.etWriteMsg);
        /*
        获取SharedPreferences句柄
        用于多方共享文件，第一个参数用来标识名称，第二个参数定义为私有
        警告:
        如果你创建一个带有MODE WROLD READABLE和MODE WORLD WRITEABLE参数的对象，那么任何其它识别出标识符的。PP可以访问数据。
        */
        SharedPreferences sharedPref = this.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        /*
        写入键值对数据
        在SharedPreferences上调用edit()创建一个SharePreferences.Editor
        用putString()写字符串方法传递你想要写入的键和值
        然后调用commit()保存
        */
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NAME, etWrite.getText().toString());
        editor.commit();
    }

    /**
     * 得到当前键名称为KEY_NAM的SharedPreferences
     * 调用getString()的方法，取出你想要的键值，如果键不存在会有一个默认值none
     * 最后把它的值取出放置在“读取”编辑框里
     */
    public void readData(View view) {
        SharedPreferences preferences = this.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE);
        EditText etRead = (EditText) findViewById(R.id.etReadMsg);
        etRead.setText(preferences.getString(KEY_NAME, "none"));
    }

    private final String FILE_NAME = "myfile";

    /**
     * 把文本内容写入文件
     * 为了在这些目录里创建一个新文件，可以使用File()构造器指定内部存储路径的方法传递文件:
     * File file二new File(context.getFilesDir(), filename);
     * 另一种办法是你还可以调用openFileOutput()得到FileOutputStream写文件到你的内部目录。
     * 这里将采用这种方法
     */
    public void saveDataIF(View view) {
        EditText etSaveIF = (EditText) findViewById(R.id.etSaveIF);
        String string = etSaveIF.getText().toString();
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readDataIF(View view) {
        /*
        getFilesDir():返回一个文件代表一个内部存储器的目录
        getCacheDir():返回一个文件代表一个内部存储器目录作为APP的临时缓存文件
        */
        String path = this.getFilesDir() + "/" + FILE_NAME;
        String content = "";
        File file = new File(path);
        try {
            InputStream inputStream = new FileInputStream(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content += line;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.d("TestFile", "文件不存在");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        EditText etReadIF = (EditText) findViewById(R.id.etReadIF);
        etReadIF.setText(content);
    }

    private final String FOLDER_NAME = "myfolder";

    /**
     * 检查外部存储器的读写是否有效
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 检查外部存储器是否可读
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 获取保存到外部存储器的文件
     * （公共文件）
     * 使用getExternalStoragePubIicDirectory()方法得到一个代表外部存储器正确目录的文件。
     * 这个方法有个参数指定你要保存的文件类型，这样能和其它公共文件逻辑上组织好，如DIRECTORY MUSIC或DIRECTORY PICTURES
     */
    public File getAlbumStorageDir(String folderName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName);
        if (!file.mkdirs()) {
            Log.e("", "Directory not created");
        }
        return file;
    }

    /**
     * 获取保存到外部存储器的文件
     * （私有文件）
     * 调用getExternalFilesDir()获得正确的目录，传递一个自定义的目录类型名称
     * 每个用这种方式创建的目录被加入到封装所有你的app外部存储文件的父目录，在你卸载app时系统会删除掉它
     */
    public File getAlbumStorageDir(Context context, String folderName) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName);
        if (!file.mkdirs()) {
            Log.e("", "Directory not created");
        }
        return file;
    }

    /**
     * 写外部文件
     */
    public void saveDataEF(View view) {
        if (!(isExternalStorageWritable())) return;

        EditText etSaveEF = (EditText) findViewById(R.id.etSaveEF);
        String string = etSaveEF.getText().toString();

        RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);

        File fileFolder;
        if (rg1.getCheckedRadioButtonId() == rb1.getId()) {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        } else if (rg1.getCheckedRadioButtonId() == rb2.getId()) {
            fileFolder = getAlbumStorageDir(this, FILE_NAME);
        } else {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        }
        File file = new File(fileFolder.getPath() + "/"+FILE_NAME);
        FileOutputStream fos = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(string);
            osw.flush();
            fos.flush();
            osw.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 读取外部文件
     */
    public void readDataEF(View view) {
        if (!(isExternalStorageReadable())) return;

        RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);

        File fileFolder;
        if (rg1.getCheckedRadioButtonId() == rb1.getId()) {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        } else if (rg1.getCheckedRadioButtonId() == rb2.getId()) {
            fileFolder = getAlbumStorageDir(this, FILE_NAME);
        } else {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        }
        File file = new File(fileFolder.getPath() + "/"+FILE_NAME);
        String content = "";
        try {
            InputStream inputStream = new FileInputStream(file);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content += line;
                }
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.d("TestFile", "文件不存在");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        EditText etReadEF = (EditText) findViewById(R.id.etReadEF);
        etReadEF.setText(content);
    }

    /**
     * 删除外部文件
     */
    public void delDataEF(View view) {
        RadioGroup rg1 = (RadioGroup) findViewById(R.id.rg1);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rb1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rb2);

        File fileFolder;
        if (rg1.getCheckedRadioButtonId() == rb1.getId()) {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        } else if (rg1.getCheckedRadioButtonId() == rb2.getId()) {
            fileFolder = getAlbumStorageDir(this, FILE_NAME);
        } else {
            fileFolder = getAlbumStorageDir(FILE_NAME);
        }
        File file = new File(fileFolder.getPath() + "/"+FILE_NAME);
        file.delete();
    }
}