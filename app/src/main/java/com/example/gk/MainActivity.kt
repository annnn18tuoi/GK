package com.example.gk

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import java.lang.Exception
import java.sql.Struct
import android.app.AlertDialog

import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    internal var dbHelper = DatabaseHelper(this)

    fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    fun cleanrEditText(){
        nameTxt.setText("");
        phoneTxt.setText("")
        typeTxt.setText("")
        idTxt.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleInserts()
        handleDelete()
        handleUpdates()
        handleViewing()
    }
    fun handleInserts(){
        thembtn.setOnClickListener{
            try {
                dbHelper.insertData(nameTxt.text.toString(),phoneTxt.text.toString(),typeTxt.text.toString())
                cleanrEditText()
                showToast("thêm thanh cong")
            }catch (e: Exception){
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    fun handleUpdates() {
        suaBtn.setOnClickListener {
            try {
                val isUpdate = dbHelper.updateData(
                        idTxt.text.toString(),
                        nameTxt.text.toString(),
                        phoneTxt.text.toString(),
                        typeTxt.text.toString()
                )
                if (isUpdate == true) {
                    showToast("Sua thanh cong")
                } else {
                    showToast("sua that bai")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }
    fun handleDelete() {
        xoabtn.setOnClickListener {
            try {
                dbHelper.deleteData(idTxt.text.toString())
                cleanrEditText()
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    fun handleViewing(){
        viewBtn.setOnClickListener(
                View.OnClickListener {
                    val res = dbHelper.allData
                    if (res.count == 0) {
                        showDialog("Error", "No Data Found")
                        return@OnClickListener
                    }
                    val buffer= StringBuffer()
                    while (res.moveToNext()){
                        buffer.append("id :" + res.getString(0) + "\n")
                        buffer.append("name :" + res.getString(1) + "\n")
                        buffer.append("phone :" + res.getString(2) + "\n")
                        buffer.append("email :" + res.getString(3) + "\n\n")
                    }
                    showDialog("Data Listing",buffer.toString())
                }
        )
    }
}