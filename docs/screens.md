# Screens

This document describes the different screens in the application and their functionality.

## Onboarding Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/OnboardingScreen.kt`
* **Description:** This screen is the first screen the user sees. It provides a brief introduction to the application and a button to proceed to the PIN entry screen.

## PIN Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/PinScreen.kt`
* **Description:** This screen prompts the user to enter their 4-digit PIN to unlock the wallet.

## Wallet Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/WalletScreen.kt`
* **Description:** This screen displays the user's licenses in a list. The user can tap on a license to view its details. There is also a "secret" trigger that navigates to the `SecretConfigScreen`.

## License Detail Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/LicenseDetailScreen.kt`
* **Description:** This screen displays the details of a license, including the user's photo, name, and other information. The user can share their license from this screen.

## Share License Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/ShareLicenseScreen.kt`
* **Description:** This screen allows the user to share their license with others. The exact sharing mechanism is not detailed in the code, but it is likely to use a standard Android sharing intent.

## Secret Config Screen

* **File:** `app/src/main/kotlin/com/hereliesaz/nolawallet/ui/screens/SecretConfigScreen.kt`
* **Description:** This is a hidden screen that can be accessed from the wallet screen. It allows the user to configure the application's "secret" settings. The exact nature of these settings is not detailed in the code.
