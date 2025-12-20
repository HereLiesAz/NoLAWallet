# Authentication

This document describes the authentication mechanisms used in the application.

The application uses a simple PIN-based authentication system to protect access to the user's wallet. The user must enter a 4-digit PIN to unlock the wallet.

The PIN is stored locally on the device using DataStore. It is not transmitted over the network.
