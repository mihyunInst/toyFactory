package com.toyFactory.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.toyFactory.model.dto.Toy;

public class ToyFactory {
	
	private Scanner sc = new Scanner(System.in); 
	private Set<Toy> toySet = new HashSet<Toy>(); // 중복된 Toy객체가 없도록 만든 Set
	private Map<Integer, String> materialTable = new HashMap<Integer, String>(); // 재료가 정리되어있는 map
	
	public ToyFactory() {
		
		materialTable.put(1, "면직물");
		materialTable.put(2, "플라스틱");
		materialTable.put(3, "유리");
		materialTable.put(4, "고무");
		
        toySet.add(new Toy("마미롱레그", 8, 36000, "분홍색", "19950805", addMaterials(1, 4)));
        toySet.add(new Toy("허기우기", 5, 12000, "파란색", "19940312", addMaterials(1, 2)));
        toySet.add(new Toy("키시미시", 5, 15000, "분홍색", "19940505", addMaterials(1, 2)));
        toySet.add(new Toy("캣냅", 8, 27000, "보라색", "19960128", addMaterials(1, 2)));
        toySet.add(new Toy("파피", 12, 57000, "빨간색", "19931225", addMaterials(1, 2, 4, 4)));
        
	}
	
	// materials에 전달받은 값들을 추가하고 Set을 반환하는 메서드
	private Set<String> addMaterials(Integer... newMaterials) {
	    Set<String> addedMaterials = new HashSet<>();
	    for (Integer material : newMaterials) {
	    	
	        // 맵에서 해당 번호에 대응하는 재료를 가져와 추가
	        String materialFromMap = materialTable.get(material);
	        if (materialFromMap != null) {
	            addedMaterials.add(materialFromMap);
	        }
	    }
	    return addedMaterials;
	}

	public void displayMenu() {
		
		int menuNum = 0;
		
		do {
			System.out.println("1. 전체 장난감 조회하기");
			System.out.println("2. 새로운 장난감 만들기");
			System.out.println("3. 장난감 삭제하기");
			System.out.println("4. 장난감 제조일 순으로 조회하기");
			System.out.println("5. 연령별 사용 가능한 장난감 리스트 조회하기");
			System.out.print("선택 : ");
			menuNum = sc.nextInt();
			
			switch (menuNum) {
            case 1: displayAllToys(); break;
            case 2: createNewToy(); break;
            case 3: deleteToy(); break;
            case 4: displayToysByManufactureDate(); break;
            case 5: displayToysByAge(); break;
            case 0: System.out.println("프로그램을 종료합니다.");break;
            default: System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }
			
			
		} while(menuNum != 0);
	}
	
	
	// 전체 장난감 조회하기
	public void displayAllToys() {
		System.out.println("전체 장난감 목록:");
		int index = 1;
        for (Toy toy : toySet) {
            System.out.println(index + ". " + toy);
            index++;
        }
    }

    // 새로운 장난감 만들기
	 public void createNewToy() {
		System.out.println("** 새로운 장난감 추가 **");
		
		System.out.print("장난감 이름 : ");
	    String name = sc.next();
		
		// 기존에 있는 장난감인지 확인
        for (Toy existingToy : toySet) {
            if (existingToy.getName().equals(name)) {
                System.out.println("이미 같은 이름을 가진 장난감이 존재합니다.");
                return;
            }
        }

	    System.out.print("사용 가능 연령: ");
	    int age = sc.nextInt();

	    System.out.print("가격: ");
	    int price = sc.nextInt();

	    System.out.print("색상: ");
	    String color = sc.next();

	    System.out.print("제조일 (YYYYMMDD 형식으로 입력): ");
	    String manufactureDate = sc.next();

	    Set<String> materials = new HashSet<>();
	    while (true) {
	        System.out.print("재료를 입력하세요 (종료하려면 'q'를 입력하세요): ");
	        String input = sc.next();
	        if (input.equals("q")) {
	            break;
	        }
	        materials.add(input);
	    }

	    Toy newToy = new Toy(name, age, price, color, manufactureDate, materials);
	    
	    toySet.add(newToy);
	    System.out.println("새로운 장난감이 추가되었습니다.");
    }


	 public void deleteToy() {
		 System.out.print("삭제할 장난감의 이름을 입력하세요: ");
	     String toyName = sc.next();

        boolean found = false;
        Iterator<Toy> iterator = toySet.iterator();
        while (iterator.hasNext()) {
            Toy toy = iterator.next();
            if (toy.getName().equals(toyName)) {
                iterator.remove();
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("장난감이 삭제되었습니다.");
        } else {
            System.out.println("해당하는 이름의 장난감을 찾을 수 없습니다.");
        }
	 }

    public void displayToysByManufactureDate() {
    	System.out.println("제조일 순으로 장난감을 정렬하여 출력합니다.");

        List<Toy> toyListSortedByDate = new ArrayList<>(toySet);
        toyListSortedByDate.sort(Comparator.comparing(Toy::getManufactureDate));

        int index = 1;
        for (Toy toy : toyListSortedByDate) {
            System.out.println(index + ". " + toy);
            index++;
        }
    }

    public void displayToysByAge() {
    	System.out.println("연령별로 사용 가능한 장난감을 출력합니다.");

        Map<Integer, List<Toy>> toysByAge = new HashMap<>();
        // toysByAge라는 이름의 새로운 해시 맵을 생성
        // -> 연령을 key로 하고, 해당 연령에 해당하는 장난감 List를 value로 가짐
        
        for (Toy toy : toySet) {
            int age = toy.getAge();
            toysByAge.putIfAbsent(age, new ArrayList<>());
            // putIfAbsent():  Map 인터페이스에서 제공되는 메서드로, 해당 키가 존재하지 않는 경우에만 값을 추가함
            // -> 맵에 해당 연령의 리스트가 없는 경우에만 새로운 리스트를 생성하여 추가
            toysByAge.get(age).add(toy);
            // toysByAge 맵에서 age(key)에 해당하는 장난감 리스트를 가져와 
            // 해당 리스트에 toy 객체를 추가함
        }

        for (Map.Entry<Integer, List<Toy>> entry : toysByAge.entrySet()) {
            int age = entry.getKey();
            List<Toy> toyList = entry.getValue();
            System.out.println("연령: " + age);
            int index = 1;
            for (Toy toy : toyList) {
                System.out.println(index + ". " + toy);
                index++;
            }
        }
    }
    
    
}
