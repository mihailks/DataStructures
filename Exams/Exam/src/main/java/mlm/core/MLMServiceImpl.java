package mlm.core;

import mlm.models.Seller;

import java.util.*;
import java.util.stream.Collectors;

public class MLMServiceImpl implements MLMService {

    private Map<String, Seller> sellersById = new LinkedHashMap<>();
    private Map<String, List<Seller>> childrenOfTheSeller = new HashMap<>();

    // seller id to parent id
    private Map<String, String> parentIdsById = new HashMap<>();

    @Override
    public void addSeller(Seller seller) {
        if (sellersById.containsKey(seller.getId())) {
            throw new IllegalArgumentException();
        }
        sellersById.put(seller.getId(), seller);
        childrenOfTheSeller.put(seller.getId(), new ArrayList<>());
    }

    @Override
    public void hireSeller(Seller parent, Seller newHire) {
        if (sellersById.containsKey(newHire.getId())) {
            throw new IllegalArgumentException();
        }
        if (!sellersById.containsKey(parent.getId())) {
            throw new IllegalArgumentException();
        }
        sellersById.put(newHire.getId(), newHire); // add new hire to the map

        childrenOfTheSeller.get(parent.getId()).add(newHire);
        childrenOfTheSeller.put(newHire.getId(), new ArrayList<>());

        parentIdsById.put(newHire.getId(), parent.getId());

        newHire.setParentId(parent.getId());
    }

    @Override
    public boolean exists(Seller seller) {
        return sellersById.containsKey(seller.getId());
    }

    @Override
    public void fire(Seller seller) {
        List<Seller> children = childrenOfTheSeller.get(seller.getId());

        String parent = parentIdsById.get(seller.getId());

        childrenOfTheSeller.remove(seller.getId());
        sellersById.remove(seller.getId());

        if (parentIdsById.containsKey(parent)) {
            childrenOfTheSeller.get(parent).addAll(children);
        }
    }

//    @Override
//    public void makeSale(Seller seller, int amount) {
//        if (seller.getParentId() == null) {
//            seller.setEarnings(seller.getEarnings() + amount);
//            seller.setSales(seller.getSales() + 1);
//            return;
//        }
//        int initialAmount = seller.getEarnings();
//
//        int numberOfParents = getNumberOfParents(seller, amount);
//        seller.setEarnings((int) (seller.getEarnings() + amount * (1 - numberOfParents * 0.05)));
//
//
//
//
//
//    }
//
//    private int getNumberOfParents(Seller seller, int amount) {
//        if (seller.getParentId() == null) {
//            return 0;
//        }
//        seller.setEarnings((int) (seller.getEarnings() + amount * (1 - 0.05)));
//        return 1 + getNumberOfParents(sellersById.get(seller.getParentId()),amount);
//    }

        @Override
    public void makeSale(Seller seller, int amount) {
        if (seller.getParentId() == null) {
            seller.setEarnings(seller.getEarnings() + amount);
            seller.setSales(seller.getSales() + 1);
            return;
        }
        int initialAmount = seller.getEarnings();
        seller.setSales(seller.getSales() + 1);
        int numberOfParents = getNumberOfParents(seller);
        seller.setEarnings((int) (seller.getEarnings() + amount * (1 - numberOfParents * 0.05)));
        giveEarningsToParentsRecursive(seller, amount, numberOfParents);




    }

    private void giveEarningsToParentsRecursive(Seller seller, int amount, int numberOfParents) {
        if (seller.getParentId() == null) {
            return;
        }
        int earnings = (int) (amount * (1 - numberOfParents * 0.05));
        seller.setEarnings(seller.getEarnings() + earnings);
        giveEarningsToParentsRecursive(sellersById.get(seller.getParentId()), earnings, numberOfParents - 1);
    }

    private int getNumberOfParents(Seller seller) {
        if (seller.getParentId() == null) {
            return 0;
        }
        return 1 + getNumberOfParents(sellersById.get(seller.getParentId()));
    }



    @Override
    public Collection<Seller> getByProfits() {
        return sellersById.values()
                .stream()
                .sorted(Comparator.comparingInt(Seller::getEarnings).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Seller> getByEmployeeCount() {
        return sellersById.values()
                .stream()
                .sorted(Comparator.comparingInt(seller -> childrenOfTheSeller.get(seller.getId()).size()))
                .collect(Collectors.toList());

//        Map<Integer, Seller> sellersByNumberOfChildren = new TreeMap<>(Comparator.comparing(Integer::intValue).reversed());
//        Map<Integer, Seller> sellersByNumberOfChildren = new LinkedHashMap<>();
//        for (Seller seller : sellersById.values()) {
//            sellersByNumberOfChildren.put(childrenOfTheSeller.get(seller.getId()).size(), seller);
//        }
//        return sellersByNumberOfChildren
//                .values()
//                .stream()
//                .sorted(Comparator.comparingInt(seller -> childrenOfTheSeller.get(seller.getId()).size()))
//                .collect(Collectors.toList());




    }




    @Override
    public Collection<Seller> getByTotalSalesMade() {
        return sellersById.values()
                .stream()
                .sorted(Comparator.comparingInt(Seller::getSales))
                .collect(Collectors.toList());
    }
}
