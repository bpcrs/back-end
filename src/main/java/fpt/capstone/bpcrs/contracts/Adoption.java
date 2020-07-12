package fpt.capstone.bpcrs.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.StaticArray16;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.6.1.
 */
@SuppressWarnings("rawtypes")
public class Adoption extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610230806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c80633de4eb171461004657806343ae80d3146100875780638588b2c5146100c0575b600080fd5b61004e6100ef565b604051808261020080838360005b8381101561007457818101518382015260200161005c565b5050505090500191505060405180910390f35b6100a46004803603602081101561009d57600080fd5b5035610135565b604080516001600160a01b039092168252519081900360200190f35b6100dd600480360360208110156100d657600080fd5b5035610152565b60408051918252519081900360200190f35b6100f76101db565b604080516102008101918290529060009060109082845b81546001600160a01b0316815260019091019060200180831161010e575050505050905090565b6000816010811061014257fe5b01546001600160a01b0316905081565b600060108211156101aa576040805162461bcd60e51b815260206004820152601860248201527f4e6f2070657420666f756e642077697468206964203132330000000000000000604482015290519081900360640190fd5b33600083601081106101b857fe5b0180546001600160a01b0319166001600160a01b03929092169190911790555090565b604051806102000160405280601090602082028036833750919291505056fea2646970667358221220ea05d31593b77d461bf27c13aed745b392e6e36adb2a0ec9bf7659fbddd271eb64736f6c634300060b0033";

    public static final String FUNC_ADOPT = "adopt";

    public static final String FUNC_ADOPTERS = "adopters";

    public static final String FUNC_GETADOPTERS = "getAdopters";

    @Deprecated
    protected Adoption(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Adoption(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Adoption(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Adoption(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> adopt(BigInteger petId) {
        final Function function = new Function(
                FUNC_ADOPT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(petId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> adopters(BigInteger param0) {
        final Function function = new Function(FUNC_ADOPTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<List> getAdopters() {
        final Function function = new Function(FUNC_GETADOPTERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray16<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static Adoption load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Adoption load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Adoption(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Adoption load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Adoption(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Adoption load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Adoption(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Adoption> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Adoption.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Adoption> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Adoption> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Adoption.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Adoption> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Adoption.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
