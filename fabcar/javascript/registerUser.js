/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { Gateway, Wallets } = require('fabric-network');
const path = require('path');

const ccpPath = path.join('..', '..', 'test-network', 'organizations','peerOrganizations', 'org1.example.com','connection-org1.json');

async function main() {
    try {

        // Create a new file system based wallet for managing identities.
        const walletPath = path.join(process.cwd(), 'wallet');
        const wallet = await Wallets.newFileSystemWallet(walletPath);
        console.log(`Wallet path: ${walletPath}`);

        // Check to see if we've already enrolled the user.
        const userIdentity = await wallet.get('user2');
        if (userIdentity) {
            console.log('An identity for the user "user2" already exists in the wallet');
            return;
        }

        // Check to see if we've already enrolled the admin user.
        const adminIdentity = await wallet.get('admin');
        if (!adminIdentity) {
            console.log('An identity for the admin user "admin" does not exist in the wallet');
            console.log('Run the enrollAdmin.js application before retrying');
            return;
        }

        // Create a new gateway for connecting to our peer node.
        const gateway = new Gateway();
        await gateway.connect(ccpPath, { wallet, identity: 'admin', discovery: { enabled: true, asLocalhost: true } });

        // Get the CA client object from the gateway for interacting with the CA.
        const client = gateway.getClient();
        const ca = client.getCertificateAuthority();
        const adminUser = await client.getUserContext('admin', false);

        // Register the user, enroll the user, and import the new identity into the wallet.
        const secret = await ca.register({ affiliation: 'org1.department1', enrollmentID: 'user2', role: 'client' }, adminUser);
        const enrollment = await ca.enroll({ enrollmentID: 'user2', enrollmentSecret: secret });
        const x509Identity = {
            credentials: {
                certificate: enrollment.certificate,
                privateKey: enrollment.key.toBytes(),
            },
            mspId: 'Org1MSP',
            type: 'X.509',
        };
        await wallet.put('user2', x509Identity);
        console.log('Successfully registered and enrolled admin user "user2" and imported it into the wallet');

    } catch (error) {
        console.error(`Failed to register user "user2": ${error}`);
        process.exit(1);
    }
}

main();
