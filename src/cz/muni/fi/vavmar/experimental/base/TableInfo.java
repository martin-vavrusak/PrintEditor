package cz.muni.fi.vavmar.experimental.base;

//Represents short information (from AD_Table) about table in database
public class TableInfo {
	
	private String tableName;
	private String description;	
	
	public TableInfo() {
	}

	public TableInfo(String tableName, String description) {
		this.tableName = tableName;
		this.description = description;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "TableInfo [tableName=" + tableName + ", description="
				+ description + "]";
	}

	
}
