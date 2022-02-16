package com.geekbrains.myfirsttests

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.myfirsttests.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val emailValidator = EmailValidator()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {
      emailInput.addTextChangedListener(emailValidator)

      saveButton.setOnClickListener {
        if (emailValidator.isValid) {
          Toast.makeText(this@MainActivity, getString(R.string.valid_email), Toast.LENGTH_SHORT)
            .show()
        } else {
          val errorEmail = getString(R.string.invalid_email)
          emailInput.error = errorEmail
          Toast.makeText(this@MainActivity, errorEmail, Toast.LENGTH_SHORT).show()
        }
      }
      compareButton.setOnClickListener {
        val pass1 = pass1Input.text.toString()
        val pass2 = pass2Input.text.toString()

        val result = PasswordComparator.comparePasswords(pass1, pass2)

        if (result) Toast.makeText(this@MainActivity,
          getString(R.string.passwords_match),
          Toast.LENGTH_SHORT).show()
        else Toast.makeText(this@MainActivity,
          getString(R.string.passwords_mismatch),
          Toast.LENGTH_SHORT).show()
      }
    }
  }
}
