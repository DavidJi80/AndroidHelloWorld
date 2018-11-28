package com.github.davidji80.helloworld.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.davidji80.helloworld.HelloWorldApplication;
import com.github.davidji80.helloworld.R;
import com.github.davidji80.helloworld.dao.SQLiteDbHelper;
import com.github.davidji80.helloworld.dao.StudentDAO;
import com.github.davidji80.helloworld.model.Student;

import java.util.List;


public class SQLiteActivity extends AppCompatActivity {
    private StudentDAO studentDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        //从Application中获取全局SQLiteDbHelper
        SQLiteDbHelper helper = ((HelloWorldApplication) getApplication()).getSQLiteDbHelper();
        studentDAO = new StudentDAO(helper);
    }


    /**
     * Students数据插入到TableLayout
     *
     * @param students
     */
    private void addRowToTable(List<Student> students) {
        TableLayout tlStudent = findViewById(R.id.tlStudent);
        int count=tlStudent.getChildCount();
        for(int i=1;i<count;i++){
            tlStudent.removeViewAt(1);
        }
        for(int i=0;i<students.size();i++) {
            Student student=students.get(i);
            //创建一行
            TableRow row = new TableRow(getApplicationContext());
            //创建显示的内容,这里创建的是一列
            TextView tvId = new TextView(getApplicationContext());
            tvId.setText(Long.toString(student.getId()));
            row.addView(tvId);
            TextView tvName = new TextView(getApplicationContext());
            tvName.setText(student.getName());
            row.addView(tvName);
            TextView tvTelNo = new TextView(getApplicationContext());
            tvTelNo.setText(student.getTelNo());
            row.addView(tvTelNo);
            TextView tvClsId = new TextView(getApplicationContext());
            tvClsId.setText(Integer.toString(student.getClsId()));
            row.addView(tvClsId);
            tlStudent.addView(row);
        }
    }

    private int getByType(){
        RadioGroup rg1 = findViewById(R.id.rg1);
        RadioButton rb1 = findViewById(R.id.rb1);
        RadioButton rb2 = findViewById(R.id.rb2);
        int byType=StudentDAO.BY_CONTENT;
        if (rg1.getCheckedRadioButtonId() == rb1.getId()) {
            byType=StudentDAO.BY_SQL;
        } else if (rg1.getCheckedRadioButtonId() == rb2.getId()) {
            byType=StudentDAO.BY_CONTENT;
        }
        return byType;
    }

    /**
     * 把EditText数据转换为student
     * @return
     */
    private Student getStudent(){
        EditText etID = findViewById(R.id.etID);
        EditText etName = findViewById(R.id.etName);
        EditText etTelNo = findViewById(R.id.etTelNo);
        EditText etClsID = findViewById(R.id.etClsID);
        Student student = new Student();
        student.setId(Long.parseLong(etID.getText().toString()));
        student.setName(etName.getText().toString());
        student.setTelNo(etTelNo.getText().toString());
        student.setClsId(Integer.parseInt(etClsID.getText().toString()));
        return student;
    }


    /**
     * 新增一个学生
     * @param view
     */
    public void add(View view) {

        studentDAO.insert(getStudent(),getByType());
    }

    /**
     * 刷新所有数据
     * @param view
     */
    public void refresh(View view) {
        List<Student> students=studentDAO.findAll(getByType());
        addRowToTable(students);
    }

    /**
     * 修改数据
     * @param view
     */
    public void update(View view) {
        studentDAO.update(getStudent());
    }

    /**
     * 删除数据
     * @param view
     */
    public void delete(View view) {
        studentDAO.delete(getStudent());
    }

}
