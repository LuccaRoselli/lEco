package com.devlucca.leconomy.utils;

import com.devlucca.leconomy.Main;

public class StringManager {
	
	public static String getPrefix(){
		return Main.plugin.getConfig().getString("Configuracoes.Prefixo").replace("&", "§");
	}

}
