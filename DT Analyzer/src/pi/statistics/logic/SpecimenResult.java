package pi.statistics.logic;

public class SpecimenResult
{
	//private ChannelResult after;
	//private ChannelResult before;

	public void calculateResult()
	{
		System.out.println("START");
		/*for (String name : before.getValue().keySet())
		{// PO CHANNELACH
			for (String waveName : before.getValue().get(name).getValue()
					.keySet())
			{// PO WAVE
				for (String statName : before.getValue().get(name).getValue()
						.get(waveName).getValue().keySet())
				{// PO STATYSTYKACH
					double varBefore = before.getValue().get(name).getValue()
							.get(waveName).getValue().get(statName);

					for (String nameAfter : after.getValue().keySet())
					{

						if (name.equals(nameAfter))
						{
							for (String waveNameAfter : after.getValue()
									.get(nameAfter).getValue().keySet())
							{
								if (waveName.equals(waveNameAfter))
								{
									for (String statNameAfter : after
											.getValue().get(nameAfter)
											.getValue().get(waveNameAfter)
											.getValue().keySet())
									{
										if (statName.equals(statNameAfter))
										{

											double varAfter = after.getValue()
													.get(nameAfter).getValue()
													.get(waveNameAfter)
													.getValue()
													.get(statNameAfter);

											if (varBefore > varAfter)
											{
												System.out
														.println("Przed wiêksze, kanal: "
																+ name
																+ ", wave: "
																+ waveName
																+ ", statystyka: "
																+ statName);
												// TODO wniosek do raportu
											} else if (varBefore < varAfter)
											{
												System.out
														.println("Po wiêksze, kanal: "
																+ name
																+ ", wave: "
																+ waveName
																+ ", statystyka: "
																+ statName);
												// TODO wniosek do raportu
											} else
											{
												System.out
														.println("Rowne, kanal: "
																+ name
																+ ", wave: "
																+ waveName
																+ ", statystyka: "
																+ statName);
												// TODO wniosek do raportu
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}*/
	}

	//public void addToVectors(VectorsToTests vectors, ChannelResult result)
	//{

		/*for (String name : result.getValue().keySet())
		{// PO CHANNELACH
			for (String waveName : result.getValue().get(name).getValue()
					.keySet())
			{// PO WAVE
				for (String statName : result.getValue().get(name).getValue()
						.get(waveName).getValue().keySet())
				{// PO STATYSTYKACH
					double value = result.getValue().get(name).getValue()
							.get(waveName).getValue().get(statName);
					vectors.addVector(waveName, statName, value);
				}
			}
		}*/
	//}

	
}
