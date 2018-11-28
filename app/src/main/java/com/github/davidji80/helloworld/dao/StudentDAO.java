package com.github.davidji80.helloworld.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.davidji80.helloworld.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    //定义表名常量
    private static final String TABLE_NAME = "students";
    //创建 students 表的 sql 语句
    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "("
            + "id integer primary key autoincrement,"
            + "name varchar(20) not null,"
            + "tel_no varchar(11) not null,"
            + "cls_id integer not null"
            + ");";
    //定义插入方式的常量
    public static final int BY_SQL = 0;
    public static final int BY_CONTENT = 1;

    private SQLiteDatabase db;

    /**
     * 构造函数
     * @param helper
     */
    public StudentDAO(SQLiteDbHelper helper){
        db=helper.open();
    }

    /**
     * 将 student 对象的值存储到 ContentValues 中
     * @param student
     * @return
     */
    private ContentValues studentToContentValues(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("tel_no", student.getTelNo());
        contentValues.put("cls_id", student.getClsId());
        return contentValues;
    }

    /**
     * 以ContentValues方式插入数据
     * @param student
     */
    public void insert(Student student){
        ContentValues values = studentToContentValues(student);
        //开始事务
        db.beginTransaction();
        try {
            db.insert(TABLE_NAME, null, values);
            //设置事务成功完成
            db.setTransactionSuccessful();
        }finally {
            //结束事务
            db.endTransaction();
        }
    }

    /**
     * 以ContentValues方式或者SQL方式插入数据
     * @param student
     */
    public void insert(Student student,int byType){
        if (byType==BY_SQL) {
            String sql = "insert into " + TABLE_NAME + " (id,name,tel_no,cls_id) " +
                    "values(" + student.getId() + ",'" + student.getName() + "','" + student.getTelNo() + "'," + student.getClsId() + ")";
            db.execSQL(sql);
        }else{
            insert(student);
        }
    }

    /**
     * 将 cursor 对象转换为 student
     * @param cursor
     * @return
     */
    private Student cursorToStudent(Cursor cursor){
        Student student=new Student();
        student.setId(cursor.getInt(cursor.getColumnIndex("id")));
        student.setName(cursor.getString(cursor.getColumnIndex("name")));
        student.setTelNo(cursor.getString(cursor.getColumnIndex("tel_no")));
        student.setClsId(cursor.getInt(cursor.getColumnIndex("cls_id")));
        return student;
    }


    /**
     * 查询表中所有数据
     * @return
     */
    public List findAll(int byType) {
        Cursor cursor;
        if (byType==BY_SQL) {
            cursor =db.rawQuery("select * from "+TABLE_NAME,null);
        }else{
            // 相当于 select * from students 语句
            cursor = db.query(TABLE_NAME, null,
                    null, null,
                    null, null, null, null);
        }


        List students=new ArrayList();
        // 不断移动光标获取值
        while (cursor.moveToNext()) {
            students.add(cursorToStudent(cursor));
        }
        // 关闭光标
        cursor.close();
        return students;
    }

    /**
     * 修改记录
     * @param student
     */
    public void update(Student student){
        ContentValues values = studentToContentValues(student);
        db.update(TABLE_NAME, values, "id=?",new String[]{Long.toString(student.getId())});
    }

    /**
     * 删除记录
     * @param student
     */
    public void delete(Student student){
        ContentValues values = studentToContentValues(student);
        db.delete(TABLE_NAME,  "id=?",new String[]{Long.toString(student.getId())});
    }
}
