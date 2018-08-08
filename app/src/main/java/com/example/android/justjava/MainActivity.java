package com.example.android.justjava;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get the user name
        EditText nameEditText = findViewById(R.id.name_edit_text);
        String customerName = nameEditText.getText().toString();
        // Check if user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        // Check if user chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        // Calculate price of order
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Get order summary string
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
        // Display message
        //  displayMessage(priceMessage);

        // Intent Send message with order details subject and text
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava coffee order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            // Show information to user that entered amount is too high.
            Toast.makeText(this, "You cannot order more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity += 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            // Show information to user that entered amount is too low.
            Toast.makeText(this, "You cannot order less than 1 coffee.", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity -= 1;
        displayQuantity(quantity);
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream inform if user wants whipped cream topping
     * @param hasChocolate    inform if user wants chocolate topping
     * @return Total Price of coffee order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Set price for one cup
        int pricePerCup = 5;

        // Add 1$ if Whipped Cream was checked by user
        if (hasWhippedCream) {
            pricePerCup += 1;
        }

        // Add 2$ if Chocolate was checked by user
        if (hasChocolate) {
            pricePerCup += 2;
        }

        // Return the total price multiplying by cups amount
        return quantity * pricePerCup;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(quantity));
    }


    /**
     * This method displays the given text on the screen.
     *
     * @param message for display order summary
     */
    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText(message);
    }

    /**
     * This method Creates order summary.
     *
     * @param customerName    name of customer
     * @param price           of the order
     * @param hasWhippedCream inform whether user wants to add whipped cream
     * @param hasChocolate    inform whether user wants to add chocolate
     * @return text summary in string
     */
    private String createOrderSummary(String customerName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        return getResources().getString(R.string.order_summary_user_name, customerName) +
                "\n" + getResources().getString(R.string.order_summary_whipped_cream, hasWhippedCream) +
                "\n" + getResources().getString(R.string.order_summary_chocolate, hasChocolate) +
                "\n" + getResources().getString(R.string.order_summary_quantity, quantity) +
                "\n" + getResources().getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price)) +
                "\n" + getResources().getString(R.string.thank_you);
    }
}
