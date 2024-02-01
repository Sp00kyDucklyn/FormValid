package com.cursokotlin.formvalidation

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cursokotlin.formvalidation.databinding.ActivityMainBinding
import com.cursokotlin.formvalidation.ui.theme.FormValidationTheme

class MainActivity : ComponentActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()
        idFocusListener()

        binding.submitButton.setOnClickListener{ submitForm()}
    }



    private fun submitForm() {

        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()
        binding.idContainer.helperText = validID()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null
        val validID = binding.idContainer.helperText == null

        if(validEmail && validPassword && validPhone && validID){
            resetForm()
        }else{
            invalidForm()
        }

    }

    private fun invalidForm() {
        var message = ""
        if(binding.emailContainer.helperText != null){
            message += "\n\nEmail: " + binding.emailContainer.helperText
        }

        if(binding.passwordContainer.helperText != null){
            message += "\n\nPassword: " + binding.passwordContainer.helperText
        }

        if(binding.phoneContainer.helperText != null){
            message += "\n\nPhone: " + binding.phoneContainer.helperText
        }

        if(binding.idContainer.helperText != null){
            message += "\n\nID: " + binding.idContainer.helperText
        }

        androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_->

            }.show()

    }

    private fun resetForm() {
        var message = "Email: " + binding.emailEditText.text
            message += "\nPassword: " + binding.passwordEditText.text
            message += "\nPhone: " + binding.phoneEditText.text
            message += "\nID: " + binding.idEditText.text


        androidx.appcompat.app.AlertDialog.Builder(this).setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_->

                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null
                binding.idEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
                binding.idContainer.helperText = getString(R.string.required)

            }.show()
    }

    private fun emailFocusListener() {
        binding.emailEditText.setOnFocusChangeListener {_, focused ->
            if (!focused){
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener {_, focused ->
            if (!focused){
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {

        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 8){
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z]*.".toRegex())){
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z]*.".toRegex())){
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&*=]*.".toRegex())){
            return "Must Contain 1 Special Character (@#\$%^&*=)"
        }

        return null
    }

    private fun validEmail(): String? {

        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Adress"
        }

        return null
    }

    private fun phoneFocusListener() {
        binding.phoneEditText.setOnFocusChangeListener {_, focused ->
            if (!focused){
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {

        val phoneText = binding.phoneEditText.text.toString()
        if(phoneText.length != 10){
            return "Must be 10 Digits"
        }
        if(!phoneText.matches(".*[0-9]*.".toRegex())){
            return "Must be all Digits"
        }

        return null
    }

    private fun idFocusListener() {
        binding.idEditText.setOnFocusChangeListener {_, focused ->
            if (!focused){
                binding.idContainer.helperText = validID()
            }
        }
    }

    private fun validID(): String? {
        val idText = binding.idEditText.text.toString()
        if(idText.length != 10){
            return "Must be 10 Digits"
        }
        if(!idText.matches(".*[0-9]*.".toRegex())){
            return "Must be all Digits"
        }

        return null

    }

}