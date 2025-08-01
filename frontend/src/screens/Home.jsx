import {
  Container,
  Typography,
  AppBar,
  Toolbar,
  IconButton,
  Avatar,
  Box,
  TextField,
  Button,
  Paper,
  Drawer,
  ListItem,
  ListItemText,
  ListItemButton,
  Menu,
  MenuItem,
  Modal,
  Rating,
  Select,
  InputLabel,
  getScopedCssBaselineUtilityClass,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";

const Home = function Home() {
  const [search, setSearch] = useState("");
  const [movie, setMovie] = useState(null);
  const [drawerOpen, setDrawerOpen] = useState(false);
  const [name, setName] = useState("");
  const [anchorEl, setAnchorEl] = useState(null);
  const [rateModalVisible, setRateModalVisible] = useState(false);
  const [rating, setRating] = useState(0);
  const [review, setReview] = useState("");
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');

  useEffect(() => {
    axios
      .get("http://localhost:8080/user/getName", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => setName(response.data))
      .catch((error) => console.log(error));
    axios
      .get("http://localhost:8080/category/getAll", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        console.log(response.data);
        setCategories(response.data);
      })
      .catch((error) => console.log(error));
  }, []);

  const saveMovie = ()=>{
    axios.post('http://localhost:8080/movie/add',{
      "title":movie.Title,
      "img":movie.Poster,
      "category":parseInt(selectedCategory)
    },{
      headers:{
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      }
    })
    .then(response=>console.log(response))
    .catch(error=>console.log(error))
  }


  const processSearch = () => {
    axios
      .get(`http://localhost:8080/movie/${search}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      })
      .then((response) => {
        setMovie({
          Title: response.data[0],
          Year: response.data[1],
          Rated: response.data[2],
          Runtime: response.data[3],
          Genre: response.data[4],
          Director: response.data[5],
          Plot: response.data[6],
          Poster: response.data[7],
        });
      })
      .catch((error) => console.log(error));
  };

  return (
    <Container
      maxWidth={false}
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "flex-start",
        alignItems: "center",
      }}
    >
      <AppBar
        sx={{
          width: "100%",
          top: 0,
          left: 0,
          zIndex: (theme) => theme.zIndex.drawer + 1,
          backgroundColor: "#db0000",
        }}
      >
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
            onClick={() => setDrawerOpen(true)}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant="h6"
            component="div"
            sx={{
              flexGrow: 1,
              fontFamily: "Roboto",
              fontWeight: 600,
              fontSize: 30,
            }}
          >
            MY MOVIES LISTS
          </Typography>
          <IconButton
            onClick={(e) => {
              setAnchorEl(e.target);
            }}
          >
            <Avatar>{name.toUpperCase()}</Avatar>
          </IconButton>
          <Menu
            open={anchorEl ? true : false}
            anchorEl={anchorEl}
            onClose={() => {
              setAnchorEl(null);
            }}
          >
            <MenuItem
              onClick={() => {
                localStorage.removeItem("token");
                navigate("/");
              }}
            >
              Logout
            </MenuItem>
          </Menu>
        </Toolbar>
      </AppBar>
      <Drawer
        anchor="left"
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
        sx={{ width: 30 }}
      >
        <Box
          sx={{
            height: "100%",
            backgroundColor: "#FFFFFF",
            width: 150,
            marginTop: 8,
          }}
        >
          <ListItemButton onClick={() => navigate("/home")}>
            <ListItem>
              <ListItemText primary="Search"></ListItemText>
            </ListItem>
          </ListItemButton>
          <ListItemButton onClick={() => navigate("/lists")}>
            <ListItem>
              <ListItemText primary="Lists"></ListItemText>
            </ListItem>
          </ListItemButton>
        </Box>
      </Drawer>
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          alignItems: "center",
          width: 500,
          marginTop: 10,
        }}
      >
        <TextField
          label="Search movie title"
          variant="outlined"
          sx={{ backgroundColor: "#564d4d", borderRadius: 2, width: 400 }}
          onChange={(e) => {
            setSearch(e.target.value);
          }}
        />
        <Button
          variant="contained"
          sx={{ backgroundColor: "#db0000" }}
          onClick={processSearch}
        >
          Search
        </Button>
      </Box>
      {movie && (
        <Paper
          elevation={3}
          sx={{
            backgroundColor: "#000000",
            border: 1,
            borderColor: "#564d4d",
            borderRadius: 3,
            width: 400,
            height: "75vh",
            marginTop: 10,
            overflow: "scroll",
          }}
        >
          <img src={movie.Poster} alt={`${movie.Title} poster`}></img>
          <Typography sx={movieDetails}>{movie.Title}</Typography>
          <Typography sx={movieDetails}>{movie.Year}</Typography>
          <Typography sx={movieDetails}>{movie.Rated}</Typography>
          <Typography sx={movieDetails}>{movie.Runtime}</Typography>
          <Typography sx={movieDetails}>{movie.Genre}</Typography>
          <Typography sx={movieDetails}>{movie.Plot}</Typography>
          <Button
            variant="contained"
            sx={{ backgroundColor: "#db0000" }}
            onClick={() => setRateModalVisible(true)}
          >
            Rate
          </Button>
          <Modal
            open={rateModalVisible}
            onClose={() => setRateModalVisible(false)}
            sx={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "space-evenly",
              alignItems: "center",
            }}
          >
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "space-between",
                alignItems: "center",
                width: 400,
                height: "70vh",
                backgroundColor: "#FFFFFF",
                borderRadius: 4,
                overflow: "hidden",
              }}
            >
              <Box
                sx={{
                  width: "100%",
                  height: "20%",
                  display: "flex",
                  flexDirection: "row",
                  justifyContent: "center",
                  alignItems: "center",
                  backgroundColor: "#db0000",
                }}
              >
                <Box sx={{ height: "50%", width: "10%", marginRight: 1 }}>
                  <img
                    src={movie.Poster}
                    alt="movie poster"
                    height="100%"
                    width="100%"
                  ></img>
                </Box>
                <Typography sx={{ fontWeight: 600, fontSize: 24 }}>
                  {movie.Title} {movie.Year}
                </Typography>
              </Box>
              <Box
                sx={{
                  width: "100%",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <Rating
                  value={rating}
                  onChange={(e) => {
                    setRating(e.target.value);
                  }}
                  required
                ></Rating>
              </Box>
              <Box
                sx={{
                  width: "100%",
                  height: "200px",
                }}
              >
                <TextField
                  label="Add Review ..."
                  onChange={(e) => {
                    setReview(e.target.value);
                  }}
                  multiline
                  required
                  sx={{
                    width: "100%",
                    height: "100%",
                    "& .MuiInputBase-root": {
                      height: "100%",
                      alignItems: "flex-start",
                    },
                    "& textarea": {
                      height: "100% !important",
                      overflow: "auto",
                    },
                  }}
                />
              </Box>
              <Box
                sx={{
                  width: "100%",
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <InputLabel id="selectedListId">
                  Please select list to add movie to
                </InputLabel>
                <Select
                  labelId="selectedListId"
                  sx={{
                    width: "40%",
                  }}
                  value={selectedCategory}
                  onChange={(e) => setSelectedCategory(e.target.value)}
                  required
                >
                  {categories.map((category) => {
                    return (
                      <MenuItem value={category.id}>{category.name}</MenuItem>
                    );
                  })}
                </Select>
              </Box>
              <Button variant="contained" onClick={()=>{
                saveMovie()
              }} sx={{ backgroundColor: "#db0000",marginBottom:1 }}>
                Save
              </Button>
            </Box>
          </Modal>
        </Paper>
      )}
    </Container>
  );
};

const movieDetails = {
  color: "#FFFFFF",
  fontSize: 20,
};

export default Home;
