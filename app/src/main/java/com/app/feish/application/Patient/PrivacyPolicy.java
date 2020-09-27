package com.app.feish.application.Patient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.feish.application.LoginActivity;
import com.app.feish.application.R;
import com.app.feish.application.VerificationChoiceActivity;
import com.app.feish.application.modelclassforapi.Contact_register;

public class PrivacyPolicy extends AppCompatActivity {
    //WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        //webview = findViewById(R.id.webView);




     /*   AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Privacy Policy");

        WebView webView = new WebView(this);
        webView.loadUrl("http://feish.online/privacy_policy");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(webView);


        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(PrivacyPolicy.this,RegisterPatient.class));

                finish();
            }
        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        startActivity(new Intent(PrivacyPolicy.this,LoginActivity.class));
                        dialog.dismiss();
                    }
                })
                .show();
*/


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
       // alert.setTitle("Privacy Policy");
       // builder.setTitle(Html.fromHtml("<font color='#FF7F27'>This is a test</font>"));
        alert.setTitle(Html.fromHtml("<font color='#FF7F27'>Privacy Policy</font>"));
        String alert1 = "This privacy policy has been compiled to better serve those who are concerned with how their 'Personally Identifiable Information' (PII) is being used online. PII, as described in US privacy law and information security, is information that can be used on its own or with other information to identify, contact, or locate a single person, or to identify an individual in context. Please read our privacy policy carefully to get a clear understanding of how we collect, use, protect or otherwise handle your Personally Identifiable Information in accordance with our website. ";

        String alert2 = "<h6><br><b> 1.What personal information do we collect from the people that visit our blog, website or app? </b><br></h6> ";

        String alert3 = "<p>When ordering or registering on our site, as appropriate, you may be asked to enter your name, email address, mailing address, phone number or other details to help you with your experience. <p>" ;

        String alert4 = "<h6><br><b>2.When do we collect information? </b><br></h6>" ;

        String alert5 = "<p>We collect information from you when you register on our site, place an order, fill out a form, Use Live Chat or enter information on our site.<p> " ;

        String alert6 = "<h6><br><b>3.How do we use your information?</b><br></h6> " ;

        String alert7 = "<p>We may use the information we collect from you when you register, make a purchase, sign up for our newsletter, respond to a survey or marketing communication, surf the website, or use certain other site features in the following ways:<p> " ;

        String alert8 = "<br> <b>. To personalize your experience and to allow us to deliver the type of content and product offerings in which you are most interested. </b><br>" ;

        String alert9 = "<br> <b>. To allow us to better service you in responding to your customer service requests. </b><br>" ;

        String alert10 = "<br> <b>. To administer a contest, promotion, survey or other site feature. </b><br>" ;

        String alert11 = "<br> <b>. To quickly process your transactions. </b> <br>" ;

        String alert12 = "<br> <b>.To ask for ratings and reviews of services or products.</b> <br> " ;

        String alert13 = "<h6><br> <b>4.How do we protect your information?</b> <br></h6>" ;

        String alert14 = "<p>Our website is scanned on a regular basis for security holes and known vulnerabilities in order to make your visit to our site as safe as possible.\n" +

                "\n" +
                "We use regular Malware Scanning.\n" +
                "\n" +
                "Your personal information is contained behind secured networks and is only accessible by a limited number of persons who have special access rights to such systems, and are required to keep the information confidential. In addition, all sensitive/credit information you supply is encrypted via Secure Socket Layer (SSL) technology.\n" +
                "\n" +
                "We implement a variety of security measures when a user places an order enters, submits, or accesses their information to maintain the safety of your personal information.\n" +
                "\n" +
                "All transactions are processed through a gateway provider and are not stored or processed on our servers. <p>" ;

        String alert15 = "<h6><br> <b>5.Do we use 'cookies'?</b> <br></h6> " ;

        String alert16 = "<p>We do not use cookies for tracking purposes\n" +
                "\n" +
                "You can choose to have your computer warn you each time a cookie is being sent, or you can choose to turn off all cookies. You do this through your browser settings. Since browser is a little different, look at your browser's Help Menu to learn the correct way to modify your cookies.\n" +
                "\n" +
                "If you turn cookies off, some features will be disabled. that make your site experience more efficient and may not function properly.\n" +
                "\n" +
                "However, you will still be able to place orders .\n <p>" ;

        String alert17 = "<h6><br> <b>6.Third-party disclosure </b> <br></h6>" ;

        String alert18 = "<p>We do not sell, trade, or otherwise transfer to outside parties your Personally Identifiable Information unless we provide users with advance notice. This does not include website hosting partners and other parties who assist us in operating our website, conducting our business, or serving our users, so long as those parties agree to keep this information confidential. We may also release information when it's release is appropriate to comply with the law, enforce our site policies, or protect ours or others' rights, property or safety. \n" +
                "\n" +
                "However, non-personally identifiable visitor information may be provided to other parties for marketing, advertising, or other uses. <p>" ;

        String alert19 = "<h6><br> <b>7.Third-party links </b> <br></h6>" ;

        String alert20 = "<p>We do not include or offer third-party products or services on our website. <p>" ;

        String alert21 = "<h6><br> <b>8.Google </b> <br></h6>" ;

        String alert22 = "<p>Google's advertising requirements can be summed up by Google's Advertising Principles. They are put in place to provide a positive experience for users. https://support.google.com/adwordspolicy/answer/1316548?hl=en \n" +
                "\n" +
                "We use Google AdSense Advertising on our website.\n" +
                "\n" +
                "Google, as a third-party vendor, uses cookies to serve ads on our site. Google's use of the DART cookie enables it to serve ads to our users based on previous visits to our site and other sites on the Internet. Users may opt-out of the use of the DART cookie by visiting the Google Ad and Content Network privacy policy.\n<p>" ;

        String alert23 = "<h6><br> <b>9.We have implemented the following: </b> <br></h6>" ;

        String alert24 = "<p>• Remarketing with Google AdSense " ;

        String alert25 = "• Google Display Network Impression Reporting " ;

        String alert26 = "• Demographics and Interests Reporting " ;

        String alert27 = "• DoubleClick Platform Integration <p>" ;

        String alert28 = "<h6><br> <b>10.COPPA (Children Online Privacy Protection Act) </b> <br></h6>" ;

        String alert29 = "<p>When it comes to the collection of personal information from children under the age of 13 years old, the Children's Online Privacy Protection Act (COPPA) puts parents in control. The Federal Trade Commission, United States' consumer protection agency, enforces the COPPA Rule, which spells out what operators of websites and online services must do to protect children's privacy and safety online.\n" +
                "\n" +
                "We do not specifically market to children under the age of 13 years old.<p> " ;

        String alert30 = "<h6><br> <b>11.CAN SPAM Act </b> <br></h6>" ;

        String alert31 = "<p>The CAN-SPAM Act is a law that sets the rules for commercial email, establishes requirements for commercial messages, gives recipients the right to have emails stopped from being sent to them, and spells out tough penalties for violations.\n" +
                "\n" +
                "We collect your email address in order to:<p> " ;

        String alert32 = "<h6><br> <b> • Send information, respond to inquiries, and/or other requests or questions\n" +
                "\n" +
                "To be in accordance with CANSPAM, we agree to the following: </b> <br></h6>\n" +
                "      • Not use false or misleading subjects or email addresses.\n" +
                "      • Identify the message as an advertisement in some reasonable way.\n" +
                "      • Include the physical address of our business or site headquarters.\n" +
                "      • Monitor third-party email marketing services for compliance, if one is used.\n" +
                "      • Honor opt-out/unsubscribe requests quickly.\n" +
                "      • Allow users to unsubscribe by using the link at the bottom of each email.\n" +
                "\n" +
                "If at any time you would like to unsubscribe from receiving future emails, you can email us at\n" +
                "      • Follow the instructions at the bottom of each email.\n" +
                "and we will promptly remove you from ALL correspondence. " ;

        alert.setMessage(Html.fromHtml(alert1 +"\n"+ alert2 +"\n"+ alert3+"\n"+ alert4+"\n"+ alert5+"\n"+ alert6+"\n"+ alert7+"\n"+ alert8+"\n"+ alert9
                +"\n"+ alert10+"\n"+ alert11+"\n"+ alert12+"\n"+ alert13+"\n"+ alert14+"\n"+ alert15+"\n"+ alert16+"\n"+ alert17+"\n"+ alert18+"\n"+ alert19
                +"\n"+ alert20+"\n"+ alert21+"\n"+ alert22+"\n"+ alert23+"\n"+ alert24+"\n"+ alert25+"\n"+ alert26+"\n"+ alert27+"\n"+ alert28+"\n"+ alert29
                +"\n"+ alert30+"\n"+ alert31+"\n"+ alert32));
        //alert.setMessage("Are you sure you want to Deactivate Profile?.");

        alert.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(PrivacyPolicy.this,RegisterPatient.class));

                finish();
            }
        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        startActivity(new Intent(PrivacyPolicy.this,LoginActivity.class));
                        dialog.dismiss();
                    }
                })
                .show();


        /* alert.setNegativeButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
   }*/
    }
    }

