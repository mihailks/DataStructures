package solar.core;

import solar.models.Inverter;
import solar.models.PVModule;

import java.util.*;
import java.util.stream.Collectors;

public class SolarServiceImpl implements SolarService {

    //invertori
    private Map<String, Inverter> invertersById = new LinkedHashMap<>();

    // moduli, paneli
    private Set<PVModule> pvModules = new LinkedHashSet<>();

    private Map<PVModule, String> panelsByInverterId = new LinkedHashMap<>();

    // list of pvModules connected to an ArrayId
    private Map<String, List<PVModule>> pvModulesByArrayId = new LinkedHashMap<>();

    //arrayId connected to the inverter
    private Map<String, List<String>> inverterAndArrays = new LinkedHashMap<>();

    @Override
    public void addInverter(Inverter inverter) {
        if (invertersById.containsKey(inverter.getId())) {
            throw new IllegalArgumentException();
        }

        invertersById.put(inverter.getId(), inverter);
    }

    @Override
    public void addArray(Inverter inverter, String arrayId) {

        if (!invertersById.containsKey(inverter.getId())) {
            throw new IllegalArgumentException();
        }

        if (pvModulesByArrayId.containsKey(arrayId)) {
            throw new IllegalArgumentException();
        }

        int maxPvArraysConnected = inverter.getMaxPvArraysConnected();
        if (inverterAndArrays.containsKey(inverter.getId())) {
            if (inverterAndArrays.get(inverter.getId()).size() >= maxPvArraysConnected) {
                throw new IllegalArgumentException();
            }
        }

        pvModulesByArrayId.put(arrayId, new ArrayList<>());
        inverterAndArrays.put(inverter.getId(), new ArrayList<>());
        inverterAndArrays.get(inverter.getId()).add(arrayId);

    }

    //ok
    @Override
    public void addPanel(Inverter inverter, String arrayId, PVModule pvModule) {
        if (!invertersById.containsKey(inverter.getId())) {
            throw new IllegalArgumentException();
        }

        if(!inverterAndArrays.get(inverter.getId()).contains(arrayId)){
            throw new IllegalArgumentException();
        }

        if (pvModules.contains(pvModule)) {
            throw new IllegalArgumentException();
        }

        pvModules.add(pvModule);
        pvModulesByArrayId.get(arrayId).add(pvModule);
        panelsByInverterId.put(pvModule, inverter.getId());

    }

    @Override
    public boolean containsInverter(String id) {
        return invertersById.containsKey(id);
    }

    @Override
    public boolean isPanelConnected(PVModule pvModule) {
        return pvModules.contains(pvModule);
    }

    @Override
    public Inverter getInverterByPanel(PVModule pvModule) {
//        if (!pvModules.contains(pvModule)) {
//            return null;
//        }
//        String arrayId = null;
//        for (Map.Entry<String, List<PVModule>> entry : pvModulesByArrayId.entrySet()) {
//            if (entry.getValue().contains(pvModule)) {
//                arrayId = entry.getKey();
//            }
//        }
//        Inverter inverter = null;
//        for (Map.Entry<String, String> entry : arrayIdByInverterId.entrySet()) {
//            if (entry.getValue().equals(arrayId)) {
//                inverter = invertersById.get(entry.getKey());
//            }
//        }
//        return inverter;
        Inverter inverter = invertersById.get(panelsByInverterId.get(pvModule));
        if (inverter == null) {
            return null;
        }
        return inverter;

    }
        //ok
    @Override
    public void replaceModule(PVModule oldModule, PVModule newModule) {
        if (!pvModules.contains(oldModule)) {
            throw new IllegalArgumentException();
        }
        if (pvModules.contains(newModule)) {
            throw new IllegalArgumentException();
        }
        String arrayId = null;
        for (Map.Entry<String, List<PVModule>> entry : pvModulesByArrayId.entrySet()) {
            if (entry.getValue().contains(oldModule)) {
                arrayId = entry.getKey();
            }
        }
        pvModulesByArrayId.get(arrayId).remove(oldModule);
        pvModulesByArrayId.get(arrayId).add(newModule);

        String inverterId = null;
        inverterId = panelsByInverterId.get(oldModule);
        panelsByInverterId.remove(oldModule);
        panelsByInverterId.put(newModule, inverterId);

        pvModules.remove(oldModule);
        pvModules.add(newModule);
    }

    @Override
    public Collection<Inverter> getByProductionCapacity() {

        List<List<PVModule>> collect = pvModulesByArrayId
                .values()
                .stream()
                .sorted(Comparator.comparing((List<PVModule> pvModules) -> pvModules.stream().mapToInt(PVModule::getMaxWattProduction).sum()).reversed())
                .collect(Collectors.toList());

        List<Inverter> inverters = new ArrayList<>();

        return invertersById
                .values()
                .stream()
                .sorted(Comparator.comparing((Inverter inverter) -> pvModulesByArrayId.get(inverterAndArrays.get(inverter.getId()).get(0)).stream().mapToInt(PVModule::getMaxWattProduction).sum()).reversed())
                .collect(Collectors.toList());


    }

    @Override
    public Collection<Inverter> getByNumberOfPVModulesConnected() {

        return invertersById
                .values()
                .stream()
                .sorted(Comparator.comparing((Inverter inverter) -> pvModulesByArrayId.get(inverterAndArrays.get(inverter.getId()).get(0)).size())
                        .thenComparing(inverter -> inverterAndArrays.get(inverter.getId()).size()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<PVModule> getByWattProduction() {
        return pvModules
                .stream()
                .sorted(Comparator.comparing(PVModule::getMaxWattProduction))
                .collect(Collectors.toList());
    }
}
