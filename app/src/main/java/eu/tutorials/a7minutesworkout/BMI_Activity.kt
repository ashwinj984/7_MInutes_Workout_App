package eu.tutorials.a7minutesworkout


import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_b_m_i_.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMI_Activity : AppCompatActivity() {
    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView : String = METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i_)

        setSupportActionBar(toolbar_bmi_Activity)
        val actionbar = supportActionBar
        if(actionbar!= null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Calculate BMI"
        }
        toolbar_bmi_Activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if(validateMetricUnits()){
                    val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue : Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                    displayBmiresult(bmi)
                }else{
                    Toast.makeText(
                        this@BMI_Activity,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                if(validateUsUnits()){
                    val usUnitHeightValueFeet : String = etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch : String = etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue : Float = etUsUnitWeight.text.toString().toFloat()

                    val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmiValue = 703 * (usUnitWeightValue /(heightValue * heightValue))
                    displayBmiresult(bmiValue)
                }else{
                    Toast.makeText(
                        this@BMI_Activity,
                        "Please enter valid values",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        makeVisibleMetricUnitsView()
        rgUnits.setOnCheckedChangeListener{ group, checkId ->
            if(checkId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitsView(){
        currentVisibleView = METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        tilUsUnitWeight.visibility = View.GONE
        llUsUnitsHeight.visibility = View.GONE

        llDiplayBMIResult.visibility = View.INVISIBLE
    }
    private fun makeVisibleUSUnitsView(){
        currentVisibleView = US_UNITS_VIEW
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        tilUsUnitWeight.visibility = View.VISIBLE
        llUsUnitsHeight.visibility = View.VISIBLE

        llDiplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBmiresult(bmi : Float){
        var bmiLabel : String
        val bmiDescription : String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        llDiplayBMIResult.visibility = View.VISIBLE
/*
        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE
*/
        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView

    }
    private fun validateMetricUnits() : Boolean{
        var isValid = true
        if(etMetricUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if(etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUsUnits() : Boolean{
        var isValid = true
        if(etUsUnitWeight.text.toString().isEmpty()){
            isValid = false
        }else if(etUsUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }else if(etUsUnitHeightFeet.text.toString().isEmpty()){
            isValid = false;
        }

        return isValid
    }
}