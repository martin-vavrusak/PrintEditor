package cz.fi.muni.vavmar.editor.DAO;

//Represents short information (from AD_Table) about table in database

import java.util.List;

public class TableInfo {
	
	private String tableName;
	private String description;	
        private String selectSQL;
        private List<String> columns;
        
	public TableInfo() {
	}

	public TableInfo(String tableName, String description, String selectSQL) {
		this.tableName = tableName;
		this.description = description;
                this.selectSQL = selectSQL;
	}

        public String getSelectSQL() {
            return selectSQL;
        }

        public void setSelectSQL(String selectSQL) {
            this.selectSQL = selectSQL;
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
