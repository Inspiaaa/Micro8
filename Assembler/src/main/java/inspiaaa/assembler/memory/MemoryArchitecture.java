package inspiaaa.assembler.memory;

import java.util.*;

public class MemoryArchitecture implements Iterable<MemoryBankInformation> {
    private final String defaultBankId;
    private final HashMap<String, MemoryBankInformation> banksById;

    public MemoryArchitecture(String defaultBank) {
        this.defaultBankId = defaultBank;
        this.banksById = new HashMap<>();
    }

    public MemoryArchitecture addBank(String id, int cellWidth, int size) {
        banksById.put(id, new MemoryBankInformation(id, cellWidth, size));
        return this;
    }

    public MemoryBankInformation getDefaultBank() {
        return banksById.get(defaultBankId);
    }

    public MemoryBankInformation getBank(String id) {
        return banksById.get(id);
    }

    @Override
    public Iterator<MemoryBankInformation> iterator() {
        return new ArrayList<>(banksById.values()).iterator();
    }
}
