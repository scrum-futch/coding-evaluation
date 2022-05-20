package com.aa.act.interview.org;

import java.util.Optional;
import java.util.Stack;
public abstract class Organization {

	private Position root;
	private int employeeCount = 0;
	public Organization() {
		root = createOrganization();
	}
	
	protected abstract Position createOrganization();
	
	
	/**
	 * hire the given person as an employee in the position that has that title
	 * 
	 * @param person
	 * @param title
	 * @return the newly filled position or empty if no position has that title
	 */
	public Optional<Position> hire(Name person, String title) {
		Optional<Position> pos = checkHire(root,person,title);
		Stack<Position> dfs = new Stack<Position>();
		dfs.push(root);
		while(!dfs.empty()) {
			for (Position p: dfs.pop().getDirectReports()) {
				dfs.push(p);
				if(pos.isEmpty()) 
					pos = checkHire(p,person,title);
				else
					return pos;
		}
	}
		return pos;
	}

	@Override
	public String toString() {
		return printOrganization(root, "");
	}
	
	//Helper function for the hire method
	private Optional<Position> checkHire(Position p,Name person,String title) {
		Optional<Position> pos = Optional.empty();
		if(p.getTitle() == title && !p.isFilled()) {
			Optional<Employee> newHire = Optional.of(new Employee(employeeCount,person));
			p.setEmployee(newHire);
			pos = Optional.of(p);
			employeeCount++;
			}
		return pos;
	}
	private String printOrganization(Position pos, String prefix) {
		StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
		for(Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
