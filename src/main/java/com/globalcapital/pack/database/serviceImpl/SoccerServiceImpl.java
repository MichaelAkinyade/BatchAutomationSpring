/*
 * package com.globalcapital.pack.database.serviceImpl;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.core.env.Environment; import
 * org.springframework.stereotype.Service;
 * 
 * import com.globalcapital.pack.database.entity.Player; import
 * com.globalcapital.pack.database.entity.Team; import
 * com.globalcapital.pack.database.repository.GenericRepository; import
 * com.globalcapital.pack.database.repository.PlayerRepository; import
 * com.globalcapital.pack.database.repository.TeamRepository; import
 * com.globalcapital.pack.database.serviceInterface.SoccerService;
 * 
 * @Service public class SoccerServiceImpl implements SoccerService {
 * 
 * @Autowired private PlayerRepository playerRepository;
 * 
 * @Autowired private TeamRepository teamRepository;
 * 
 * @Autowired private Environment env;
 * 
 * @Autowired private GenericRepository genericRepository;
 * 
 * public List<String> getAllTeamPlayers(long teamId) { List<String> result =
 * new ArrayList<String>(); List<Player> players =
 * playerRepository.findByTeamId(teamId); for (Player player : players) {
 * result.add(player.getName()); }
 * 
 * return result; }
 * 
 * 
 * 
 * public void addBarcelonaPlayer(String name, String position, int number) {
 * Long seq = (long) 1; Team barcelona = teamRepository.findOne((long) 2);
 * 
 * Player newPlayer = new Player(); newPlayer.setId(seq);
 * newPlayer.setName(name); newPlayer.setPosition(position);
 * newPlayer.setNum(number); newPlayer.setTeam(barcelona);
 * playerRepository.save(newPlayer); }
 * 
 * 
 * 
 * @Override public void getSpecificPlayerByID(int id) {
 * 
 * Player newPlayer = new Player(); newPlayer =
 * playerRepository.findUserByID(id);
 * System.out.println("******************New Player Name is "+newPlayer.getName(
 * )+"****************************");
 * 
 * 
 * }
 * 
 * 
 * @Override public List<Object[]> getMultipleTbales() {
 * 
 * //Multiple tables record test
 * 
 * @SuppressWarnings("unused") List<Object[]> multipleTbales = new
 * ArrayList<>();
 * 
 * return multipleTbales =
 * genericRepository.findRunningQueries(env.getProperty("solife.server.schema"))
 * ; }
 * 
 * 
 * }
 */