package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.databinding.DataBindingUtil
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.calcExp.text = ""
        binding.calcResult.text = ""
        binding.calc0.setOnClickListener { evaluateExpression("0",binding,true) }
        binding.calc1.setOnClickListener { evaluateExpression("1",binding,true) }
        binding.calc2.setOnClickListener { evaluateExpression("2",binding,true) }
        binding.calc3.setOnClickListener { evaluateExpression("3",binding,true) }
        binding.calc4.setOnClickListener { evaluateExpression("4",binding,true) }
        binding.calc5.setOnClickListener { evaluateExpression("5",binding,true) }
        binding.calc6.setOnClickListener { evaluateExpression("6",binding,true) }
        binding.calc7.setOnClickListener { evaluateExpression("7",binding,true) }
        binding.calc8.setOnClickListener { evaluateExpression("8",binding,true) }
        binding.calc9.setOnClickListener { evaluateExpression("9",binding,true) }
        binding.del.setOnClickListener { evaluateExpression("DEL",binding,false) }
        binding.clear.setOnClickListener { evaluateExpression("CLEAR",binding,false) }
        binding.divide.setOnClickListener { evaluateExpression("/",binding,false) }
        binding.multiply.setOnClickListener { evaluateExpression("*",binding,false) }
        binding.add.setOnClickListener { evaluateExpression("+",binding,false) }
        binding.subtract.setOnClickListener { evaluateExpression("-",binding,false) }
        binding.equals.setOnClickListener { evaluateExpression("=",binding,false) }
        binding.decimal.setOnClickListener { evaluateExpression(".",binding,false) }
    }

    fun doesContainDecimal(exp:String):Boolean{
        for(c in exp){
            if(c=='.')return true
        }
        return false
    }
    class Expression{
        var operator:String = ""
        var operand1:Double? = 0.0
        var operand2:Double? = 0.0
    }
    private fun parseExpression(expression:String):Expression{
        println("Inside parseExpression")
        val exp:Expression= Expression()
        for(c in expression){
            if(c=='+'||c=='-'||c=='*'||c=='/'){
                exp.operator = c.toString()
                val list = expression.split(c)
                exp.operand1 = (list[0].toDoubleOrNull())
                exp.operand2 = (list[1].toDoubleOrNull())
            }
        }
        return exp
    }

    fun evaluate(expression: Expression):Double{
        var ans:Double? = expression.operand1
        if(expression.operator=="*"){
            ans= ans!! * expression.operand2!!
        }
        else if(expression.operator=="-"){
            ans= ans!! - expression.operand2!!
        }
        else if(expression.operator=="/"){
            ans= ans!! / expression.operand2!!
        }
        else if(expression.operator=="+"){
            ans= ans!! + expression.operand2!!
        }
        return ans!!
    }

    fun evaluateExpression(pressed:String,binding: ActivityMainBinding,isNumber:Boolean){
        if(isNumber||pressed=="."){
            if(pressed=="."&&doesContainDecimal(binding.calcExp.text.toString()))return
            binding.calcExp.setText(binding.calcExp.text.toString() + pressed)
        }
        else if(pressed=="DEL"){
            binding.calcExp.setText(binding.calcExp.text.toString().substring(0,binding.calcExp.text.toString().length-1))
            return
        }
        else if(pressed=="CLEAR"){
            binding.calcResult.setText("")
            binding.calcExp.setText("")
        }
        else if(pressed=="="){
//            println("= pressed")
//            println(binding.calcExp.text)
//            println(binding.calcExp.text)
//            val exp:Expression = parseExpression(binding.calcExp.text.toString())
//            val result = evaluate(exp)
//            binding.calcExp.setText(result.toString())
//            binding.calcResult.setText(result.toString())
            val text = binding.calcExp.text.toString()
            val exp = ExpressionBuilder(text).build()
            val result = exp.evaluate()
            print(result)
            binding.calcExp.setText(result.toString())
        }
        else{
            binding.calcExp.setText(binding.calcExp.text.toString() + pressed)
        }
    }
}