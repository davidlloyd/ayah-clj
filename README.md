A Clojure library for using Are You A Human tests.

Author: david lloyd (http://www.touchsoftware.cc) Copyright 2013, All rights reserved.
License: [Creative Commons BY-ND](http://creativecommons.org/licenses/by-nd/3.0/legalcode)

Quick Start Guide:

   *   Get Ayah key. [http://portal.areyouahuman.com/signup](http://portal.areyouahuman.com/signup)
   *   Place the key appropriately in your application. In this sample, change handler.clj lines 19-20 replacing the numbers there with your keys.
   *   The file ayah/client.clj is the only file here you need in your application.
   *   Your application must have the following dependencies for Leinengen [org.clojure/data.json "0.2.2"] [clj-http "0.7.6"] (change for Maven or other method)
   *   In your aaplication, call (ayah-client/get-publisher-html YOUR_PUBLISHER_KEY) to get the game code.
   *   In your application, call (ayah-client/score-result YOUR_SCORING_KEY (request :session_secret)) to see if the user is a human
   *   You can call (ayah-client/record-conversion (request :session_secret)) to record the successful A-B test conversion.
   *   All methods can optionally take a last parameter specifying a dedicated web service url if you have added that to your account.
