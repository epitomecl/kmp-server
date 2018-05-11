## Install Bitcoind

    sudo add-apt-repository ppa:bitcoin/bitcoin
    sudo apt-get update
    sudo apt-get install bitcoind

**Configure**

    mkdir -p /data/ubuntu/.bitcoin
    vim /data/ubuntu/.bitcoin/bitcoin.conf
    mkdir ~/.bitcoin
    ln -s /data/ubuntu/.bitcoin/bitcoin.conf ~/.bitcoin/
    ls -l ~/.bitcoin/

Insert the following lines into the bitcoin.conf, and replce with your username and password.

    server=1
    daemon=1

    # If run on the test network instead of the real bitcoin network
    testnet=1

    datadir=/data/ubuntu/.bitcoin
    # You must set rpcuser and rpcpassword to secure the JSON-RPC api
    # Please make rpcpassword to something secure, `5gKAgrJv8CQr2CGUhjVbBFLSj29HnE6YGXvfykHJzS3k` for example.
    # Listen for JSON-RPC connections on <port> (default: 8332 or testnet: 18332)
    rpcuser=kmp
    rpcpassword=kmptest
    rpcport=18332
    rpcallowip=0.0.0.0/0

    # Notify when receiving coins
    # walletnotify=/usr/local/sbin/rabbitmqadmin publish routing_key=peatio.deposit.coin payload='{"txid":"%s", "channel_key":"satoshi"}'

**Start bitcoin**

    bitcoind
    ls /data/ubuntu/.bitcoin
    du -sh /data/ubuntu/.bitcoin/
    tail -f /data/ubuntu/.bitcoin/testnet3/debug.log
    curl -v --data-binary '{"jsonrpc":"1.0","id":"curltext","method":"getblockchaininfo","params":[]}' -H 'content-type: text/plain;' http://kmp:kmptest@127.0.0.1:18332/



